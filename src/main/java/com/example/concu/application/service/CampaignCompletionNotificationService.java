package com.example.concu.application.service;

import com.example.concu.application.dto.CampaignCompletionNotificationDto;

public interface CampaignCompletionNotificationService {
    void sendNotification(CampaignCompletionNotificationDto dto);
}
