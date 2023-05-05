package com.example.ticket.concurrency.domain.campaign.entity.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CampaignInfo {
    Long campaignId;
    Long maxApplierCount;

}
