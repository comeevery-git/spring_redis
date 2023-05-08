package com.example.concu.application.dto;

import lombok.*;

@Builder
@Data
@EqualsAndHashCode
public class CampaignCompletionNotificationDto {
    private Long campaignId;
    private String title;
}
