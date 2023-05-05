package com.example.concu.applier.domain.service.impl;

import com.example.concu.applier.domain.model.Applier;
import com.example.concu.applier.web.dto.ReqApply;
import com.example.concu.applier.domain.service.ApplierService;
import com.example.concu.campaign.application.CampaignQueryService;
import com.example.concu.campaign.domain.model.Campaign;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Slf4j
@RequiredArgsConstructor
@Service
public class ApplierServiceImpl implements ApplierService {
    private final RedisTemplate redisTemplate;
    private final RedissonClient redissonClient;
    private final CampaignQueryService campaignQueryService;
    private final ObjectMapper objectMapper;


    /**
     * 지원하기
     * @param req
     * @return
     * @throws Exception
     */
    @Override
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
                        .build();

                try {
                    zSetOperations.add(key, objectMapper.writeValueAsString(applier), rank);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

            } else {
                // 락을 획득하지 못한 경우
                throw new RuntimeException("Failed to acquire lock");
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
        return campaignQueryService.getCampaignById(campaignId);
    }

}
