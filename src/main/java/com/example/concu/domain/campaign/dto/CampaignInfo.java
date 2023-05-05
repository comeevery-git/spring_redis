package com.example.concu.domain.campaign.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CampaignInfo {
    Long campaignId;
    Long maxApplierCount;

}
