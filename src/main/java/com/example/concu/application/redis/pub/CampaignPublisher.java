package com.example.concu.application.redis.pub;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CampaignPublisher {
    private final RedissonClient redissonClient;

    public CampaignPublisher(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * 해당 채널로 메시지 발행
     * @param channelName
     * @param message
     */
    public void publish(String channelName, String message) {
        if(channelName == null || message == null) {
            log.error("Channel or message is null. channel: {}, message: {}", channelName, message);
            return;
        }
        redissonClient.getTopic(channelName).publish(message);

    }

}

