package com.example.concu.application.service;

import com.example.concu.infrastructure.campaign.entity.Campaign;

public interface CampaignApplicationService {
    Campaign getCampaignByIdAndActive(Long campaignId) throws Exception;
}
