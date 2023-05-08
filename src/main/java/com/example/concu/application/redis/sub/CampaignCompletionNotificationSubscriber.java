package com.example.concu.application.redis.sub;


import com.example.concu.application.dto.CampaignCompletionNotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Component
@RequiredArgsConstructor
public class CampaignCompletionNotificationSubscriber {
    private final RedissonClient redissonClient;
    private MessageListener<CampaignCompletionNotificationDto> messageListener;


    @PostConstruct
    public void setup() {
        messageListener = (channel, message) -> {
            CampaignCompletionNotificationDto notificationDto = message;
            log.debug("Notification received: " + notificationDto.toString());

            Long campaignId = notificationDto.getCampaignId();
            String title = notificationDto.getTitle();
            // 알림 메시지 출력
            String msg = String.format("'%s' 캠페인이 성공하였습니다. 참여해주셔서 감사합니다. (%s)", title, campaignId);
            log.info(msg);

        };
        redissonClient.getTopic("campaignCompletionNotification").addListener(CampaignCompletionNotificationDto.class, messageListener);
    }

    @PreDestroy
    public void cleanup() {
        redissonClient.getTopic("campaignCompletionNotification").removeListener(messageListener);
    }

}
