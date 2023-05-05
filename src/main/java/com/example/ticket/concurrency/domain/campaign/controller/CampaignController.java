package com.example.ticket.concurrency.domain.campaign.controller;

import com.example.ticket.concurrency.domain.campaign.entity.response.CampaignInfo;
import com.example.ticket.concurrency.domain.campaign.service.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/campaigns")
public class CampaignController {
    private final CampaignService campaignService;


    @GetMapping("/{campaignId}")
    public CampaignInfo getCampaignByCampaignId(@PathVariable Long campaignId) throws Exception {
        CampaignInfo result = campaignService.getCampaignByCampaignId(campaignId);
        return result;
    }

    @GetMapping
    public List<CampaignInfo> getCampaigns() throws Exception {
        List<CampaignInfo> resultList = campaignService.getCampaigns();
        return resultList;
    }

    @PostMapping
    public void createCampaigns() throws Exception {
        campaignService.createCampaigns();
    }

}
