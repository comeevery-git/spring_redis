package com.example.concu.application;

import com.example.concu.infrastructure.campaign.entity.Campaign;

public interface CampaignApplicationService {
    Campaign getCampaignById(Long campaignId) throws Exception;
}
