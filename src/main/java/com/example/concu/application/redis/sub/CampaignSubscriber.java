package com.example.concu.application.redis.sub;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CampaignSubscriber {

    /**
     * 수신 된 메세지 처리
     * @param channelName
     * @param message
     */
    public void subscribe(String channelName, String message) {
        log.info("Received message on channel " + channelName);
        if(channelName == null || message == null) {
            log.error("Channel or message is null. channelName: {}, message: {}", channelName, message);
            return;
        }
        handleCampaignCompletionNotification(message);

    }

    private void handleCampaignCompletionNotification(String message) {
//        String title = campaignCompletionNotificationInfo.getTitle();
//        String name = CampaignCompletionNotificationInfo.getName();
        // 알림 메시지 출력
        String msg = String.format(message);
//        String msg = String.format("'%s' 캠페인이 성공하였습니다. '%s'님, 참여해주셔서 감사합니다.", title, name);
        log.info(msg);
    }

}
