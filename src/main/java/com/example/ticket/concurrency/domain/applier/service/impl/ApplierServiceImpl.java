package com.example.ticket.concurrency.domain.applier.service.impl;

import com.example.ticket.concurrency.domain.applier.entity.Applier;
import com.example.ticket.concurrency.domain.applier.entity.Campaign;
import com.example.ticket.concurrency.domain.applier.entity.request.ReqApply;
import com.example.ticket.concurrency.domain.applier.repository.CampaignRepository;
import com.example.ticket.concurrency.domain.applier.service.ApplierService;
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
    private final CampaignRepository campaignRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void applyBase() {
        createCampaign();
    }

    @Override
    public Long apply(ReqApply req) throws Exception {
        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
        Long campaignId = req.getCampaignId();
        log.info("campaignId: {}", campaignId);
        LocalDateTime now = LocalDateTime.now();
        String key = "applier:apply:" + req.getCampaignId();

        Campaign campaign = campaignRepository.findById(campaignId).orElse(null);
        Long maxApplierCount = campaign.getMaxApplierCount();
        log.info("maxApplierCount: {}", maxApplierCount);

        RLock lock = redissonClient.getFairLock(String.valueOf(campaignId));
        boolean isLocked = lock.tryLock(10, 30, TimeUnit.SECONDS);
        // 락을 획득하기 위해 10초 대기
        try {
            if (isLocked) {
                Set<Object> alreadyCampaignApply = zSetOperations.range(key, 0, maxApplierCount);
                if (alreadyCampaignApply.size() >= maxApplierCount) {
                    throw new RuntimeException("이미 신청이 마감되었습니다.");
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
                        throw new RuntimeException("이미 신청하셨습니다.");
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

        if(zSetOperations.size(key) != null) {
            return zSetOperations.size(key);
        } else {
            return Long.valueOf(0);
        }
    }

    private void createCampaign() {
        List<Campaign> campaigns = new ArrayList<>();
        Campaign campaign1 = Campaign.builder()
                .campaignId(1L)
                .maxApplierCount(3L)
                .build();
        campaigns.add(campaign1);
        Campaign campaign2 = Campaign.builder()
                .campaignId(2L)
                .maxApplierCount(10L)
                .build();
        campaigns.add(campaign2);
        Campaign campaign3 = Campaign.builder()
                .campaignId(3L)
                .maxApplierCount(50L)
                .build();
        campaigns.add(campaign3);
        Campaign campaign4 = Campaign.builder()
                .campaignId(4L)
                .maxApplierCount(100L)
                .build();
        campaigns.add(campaign4);

        campaignRepository.saveAll(campaigns);
    }

}
