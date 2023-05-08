package com.example.concu.application.service.impl;

import com.example.concu.application.dto.CampaignCompletionNotificationDto;
import com.example.concu.application.service.CampaignCompletionNotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

@RequiredArgsConstructor
public class CampaignCompletionNotificationServiceImpl implements CampaignCompletionNotificationService {

    private final RedisTemplate<String, CampaignCompletionNotificationDto> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void sendNotification(CampaignCompletionNotificationDto campaignCompletionNotificationDto) {
        try {
            redisTemplate.convertAndSend("campaignCompletionNotification", objectMapper.writeValueAsString(campaignCompletionNotificationDto));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert campaignCompletionNotificationDto to json string.", e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
