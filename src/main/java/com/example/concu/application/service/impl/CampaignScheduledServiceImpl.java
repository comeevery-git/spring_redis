package com.example.concu.application.service.impl;

import com.example.concu.application.dto.CampaignCompletionNotificationInfo;
import com.example.concu.application.redis.BaseRedisQueue;
import com.example.concu.application.redis.pub.CampaignPublisher;
import com.example.concu.application.service.CampaignScheduledService;
import com.example.concu.infrastructure.applier.entity.Applier;
import com.example.concu.infrastructure.campaign.entity.Campaign;
import com.example.concu.infrastructure.campaign.enums.CampaignStatus;
import com.example.concu.infrastructure.campaign.repository.CampaignRepository;
import com.example.concu.utils.GsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class CampaignScheduledServiceImpl implements CampaignScheduledService {
    private final CampaignRepository campaignRepository;
    private final CampaignPublisher campaignPublisher;
    private final RedisTemplate redisTemplate;

    /**
     * 캠페인 상태 변경
     */
    @Scheduled(fixedRate = 60000)
    public void updateCampaignStatusScheduled() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        List<Campaign> savedCampaignList = new ArrayList<>();

        // 삭제 된 캠페인 제외하고 변경
        List<Campaign> campaigns = campaignRepository.findAllByCampaignStatusNot(CampaignStatus.DELETED);
        campaigns.forEach(campaign -> {
            if (campaign.getStartedAt().isBefore(now) && campaign.getEndedAt().isAfter(now)) {
                campaign.setCampaignStatus(CampaignStatus.ACTIVE);
            } else if (campaign.getEndedAt().isBefore(now)) {
                campaign.setCampaignStatus(CampaignStatus.ENDED);
            }
            savedCampaignList.add(campaign);
        });
        campaignRepository.saveAll(savedCampaignList);

    }


    /**
     * 캠페인 성공 알림
     */
//    @Scheduled(cron = "0 0 22 * * *", zone = "Asia/Seoul") // 매일 밤 10시
    @Scheduled(fixedRate = 60000) // 1분마다
    public void sendCampaignCompletionNotification() {
        List<Campaign> campaigns = campaignRepository.findAllByCampaignStatusNot(CampaignStatus.DELETED);

        campaigns.forEach(campaign -> {
            Long campaignId = campaign.getCampaignId();
            String key = "applier:apply:" + campaignId;
            BaseRedisQueue applierQueue = new BaseRedisQueue(redisTemplate, key);
            applierQueue.rangeAllByKey(key).forEach(applierString -> {
                Applier applier = GsonUtils.fromJson(applierString, Applier.class);
                CampaignCompletionNotificationInfo info = CampaignCompletionNotificationInfo.builder()
                        .campaignId(campaignId)
                        .title(campaign.getTitle())
                        .name(applier.getName())
                        .build();
                log.info("Sending campaign completion notification for campaign {}", campaign.getCampaignId());

                // 채널(토픽) 발행
                campaignPublisher.publish("campaignCompletionNotification", info.toString());
            });
        });

    }

}
