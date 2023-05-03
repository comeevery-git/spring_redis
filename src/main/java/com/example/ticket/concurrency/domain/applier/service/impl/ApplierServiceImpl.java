package com.example.ticket.concurrency.domain.applier.service.impl;

import com.example.ticket.concurrency.domain.applier.entity.Applier;
import com.example.ticket.concurrency.domain.applier.entity.Campaign;
import com.example.ticket.concurrency.domain.applier.entity.request.ReqApply;
import com.example.ticket.concurrency.domain.applier.repository.CampaignRepository;
import com.example.ticket.concurrency.domain.applier.service.ApplierService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;


@RequiredArgsConstructor
@Service
public class ApplierServiceImpl implements ApplierService {

    private final RedisTemplate redisTemplate;
    private final CampaignRepository campaignRepository;

    private final ObjectMapper objectMapper;

    @Override
    public Long apply(ReqApply req) throws Exception {
        Campaign campaign = createCampaign();

        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
        LocalDateTime now = LocalDateTime.now();
        String key = "applier:apply:" + req.getCampaignId();

//        Optional<Campaign> campaignOptional = campaignRepository.findById(req.getCampaignId());
//        campaignOptional.ifPresent(campaign -> {
        Long maxApplierCount = campaign.getMaxApplierCount();
        Set<Object> alreadyCampaignApply = zSetOperations.range(key, 0, maxApplierCount);
        if (alreadyCampaignApply.size() >= maxApplierCount) {
            throw new RuntimeException("이미 신청이 마감되었습니다.");
        }

        Integer rank = 1;
        Set<Object> alreadyCampaignApplyLast = zSetOperations.range(key, alreadyCampaignApply.size() - 1, alreadyCampaignApply.size());
        if(!alreadyCampaignApplyLast.isEmpty()) {
            Applier applierLast = objectMapper.readValue(alreadyCampaignApplyLast.iterator().next().toString(), Applier.class);

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

        zSetOperations.add(key, objectMapper.writeValueAsString(applier), rank);
//        });

        if(zSetOperations.size(key) != null) {
            return zSetOperations.size(key);
        } else {
            return Long.valueOf(0);
        }
    }

    private Campaign createCampaign() {
        Campaign campaign = Campaign.builder()
                .campaignId(20010L)
                .maxApplierCount(3L)
                .build();
        return campaignRepository.save(campaign);
    }

}
