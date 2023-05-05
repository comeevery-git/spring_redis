package com.example.concu.campaign.web.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CampaignInfo {
    Long campaignId;
    Long maxApplierCount;

}
