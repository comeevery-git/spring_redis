package com.example.ticket.concurrency.domain.campaign.service;

import com.example.ticket.concurrency.domain.campaign.entity.response.CampaignInfo;

import java.util.List;

public interface CampaignService {
    CampaignInfo getCampaignByCampaignId(Long campaignId) throws Exception;
    List<CampaignInfo> getCampaigns() throws Exception;
    void createCampaigns();


}
