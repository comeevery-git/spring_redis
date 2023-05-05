package com.example.concu.campaign.domain.service;

import com.example.concu.campaign.web.dto.CampaignInfo;

import java.util.List;

public interface CampaignService {
    CampaignInfo getCampaignByCampaignId(Long campaignId) throws Exception;
    List<CampaignInfo> getCampaigns() throws Exception;
    void createCampaigns();


}
