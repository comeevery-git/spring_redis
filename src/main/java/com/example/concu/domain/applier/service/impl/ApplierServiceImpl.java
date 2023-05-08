package com.example.concu.domain.applier.service.impl;

import com.example.concu.application.service.CampaignApplicationService;
import com.example.concu.infrastructure.applier.entity.ApplierUser;
import com.example.concu.infrastructure.applier.enums.ApplierUserStatus;
import com.example.concu.presentation.dto.ReqApply;
import com.example.concu.infrastructure.applier.entity.Applier;
import com.example.concu.infrastructure.applier.repository.ApplierUserRepository;
import com.example.concu.domain.applier.service.ApplierService;
import com.example.concu.infrastructure.campaign.entity.Campaign;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@Slf4j
@RequiredArgsConstructor
@Service
public class ApplierServiceImpl implements ApplierService {
    private final RedisTemplate redisTemplate;
    private final RedissonClient redissonClient;
    private final CampaignApplicationService campaignQueryService;
    private final ObjectMapper objectMapper;
    private final ApplierUserRepository applierUserRepository;


    /**
     * 지원하기
     * @param req
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Long apply(ReqApply req) throws Exception {
        // Redis Sorted Set
        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();

        Long campaignId = req.getCampaignId();
        LocalDateTime now = LocalDateTime.now();
        String key = "applier:apply:" + req.getCampaignId();
        log.info("campaignId: {}, Redis key: {}", campaignId, key);

        // 캠페인 정보 조회
        Campaign campaign = applyToCampaign(campaignId);
        Long maxApplierCount = campaign.getMaxApplierCount();
        log.info("maxApplierCount: {}", maxApplierCount);

        RLock lock = redissonClient.getFairLock(String.valueOf(campaignId));
        boolean isLocked = lock.tryLock(10, 30, TimeUnit.SECONDS);
        // 락을 획득하기 위해 10초 대기
        try {
            if (isLocked) {
                Set<Object> alreadyCampaignApply = zSetOperations.range(key, 0, maxApplierCount);
                if (alreadyCampaignApply.size() >= maxApplierCount) {
                    throw new Exception("이미 신청이 마감되었습니다.");
                }
                Integer rank = 1;
                Set<Object> alreadyCampaignApplyLast = zSetOperations.range(key, alreadyCampaignApply.size() - 1, alreadyCampaignApply.size());
                if(!alreadyCampaignApplyLast.isEmpty()) {
                    Applier applierLast = null;
                    try {
                        applierLast = objectMapper.readValue(alreadyCampaignApplyLast.iterator().next().toString(), Applier.class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    if(applierLast.getMemberId().equals(req.getMemberId())) {
                        throw new Exception("이미 신청하셨습니다.");
                    }
                    rank = applierLast.getRank() + 1;
                }

                Applier applier = Applier.builder()
                        .rank(rank)
                        .campaignId(req.getCampaignId())
                        .memberId(req.getMemberId())
                        .name(req.getName())
                        .applyTime(now)
                        .applierUserStatus(ApplierUserStatus.ACTIVE)
                        .build();

                // 지원자 정보 DB 저장
                createApplierUser(applier);
                // 지원자 정보 Redis 저장
                addApplierToQueue(key, applier, rank);

            } else {
                // 락을 획득하지 못한 경우
                throw new Exception("Failed to acquire lock");
            }
        } finally {
            if (isLocked) {
                lock.unlock();
            }
        }

        // Redis 해당 key 총 size 반환
        if(zSetOperations.size(key) != null) {
            return zSetOperations.size(key);
        } else {
            return Long.valueOf(0);
        }
    }


    public Campaign applyToCampaign(Long campaignId) throws Exception {
        return campaignQueryService.getCampaignByIdAndActive(campaignId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void createApplierUser(Applier applier) throws Exception {
        try {
            ApplierUser applierUser = ApplierUser.builder()
                    .campaignId(applier.getCampaignId())
                    .memberId(applier.getMemberId())
                    .applierUserStatus(applier.getApplierUserStatus())
                    .build();
            applierUserRepository.save(applierUser);
        } catch (Exception e) {
            log.error("Failed to save applierUser in DB.", e);
            throw e;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void addApplierToQueue(String key, Applier applier, Integer rank) {
        redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                try {
                    operations.opsForZSet().add(key, objectMapper.writeValueAsString(applier), rank);
                } catch (JsonProcessingException e) {
                    operations.discard();
                    throw new RuntimeException("Failed to add applier to queue.", e);
                } catch (Exception e) {
                    operations.discard();
                    e.printStackTrace();
                }
                operations.exec();
                return null;
            }
        });
    }

}
