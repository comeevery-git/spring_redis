package com.example.concu.application.redis.pub;

import com.example.concu.application.dto.CampaignCompletionNotificationDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class CampaignCompletionNotificationPublisher {
    private final RedisTemplate<String, Object> redisTemplate;
    private final RTopic campaignCompletionNotificationTopic;
    private final ObjectMapper objectMapper;

    public CampaignCompletionNotificationPublisher(RedisTemplate<String, Object> redisTemplate, RedissonClient redissonClient, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.campaignCompletionNotificationTopic = redissonClient.getTopic("campaignCompletionNotification");
        this.objectMapper = objectMapper;
    }

    public void publishCampaignCompletionNotification(Long campaignId, String title) {
        try {
            CampaignCompletionNotificationDto campaignCompletionNotificationDto = CampaignCompletionNotificationDto.builder()
                    .campaignId(campaignId)
                    .title(title)
                    .build();
            String message = objectMapper.writeValueAsString(campaignCompletionNotificationDto);
            redisTemplate.convertAndSend("campaignCompletionNotification", message);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert message to JSON.", e);
        }
    }


}

