package com.example.concu.presentation.campaign.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
public class CampaignInfo implements Serializable {
    Long campaignId;
    Long maxApplierCount;

}
