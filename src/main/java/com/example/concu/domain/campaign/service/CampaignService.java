package com.example.concu.domain.campaign.service;

import com.example.concu.domain.campaign.dto.CampaignInfo;

import java.util.List;

public interface CampaignService {
    CampaignInfo getCampaignByCampaignId(Long campaignId) throws Exception;
    List<CampaignInfo> getCampaigns() throws Exception;
    void createCampaigns();


}
