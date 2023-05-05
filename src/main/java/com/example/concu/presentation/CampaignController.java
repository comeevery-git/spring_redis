package com.example.concu.presentation;

import com.example.concu.domain.campaign.dto.CampaignInfo;
import com.example.concu.domain.campaign.service.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(value = "campaigns", key = "#root.methodName")
    public List<CampaignInfo> getCampaigns() throws Exception {
        List<CampaignInfo> resultList = campaignService.getCampaigns();
        return resultList;
    }

    @PostMapping
    public void createCampaigns() throws Exception {
        campaignService.createCampaigns();
    }

}
