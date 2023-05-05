package com.example.ticket.concurrency.domain.campaign.service;

import com.example.ticket.concurrency.domain.campaign.entity.Campaign;

public interface CampaignQueryService {
    Campaign getCampaignById(Long campaignId) throws Exception;
}
