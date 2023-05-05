package com.example.concu.campaign.application;

import com.example.concu.campaign.domain.model.Campaign;

public interface CampaignQueryService {
    Campaign getCampaignById(Long campaignId) throws Exception;
}
