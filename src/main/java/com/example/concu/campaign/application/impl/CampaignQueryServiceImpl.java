package com.example.concu.campaign.application.impl;

import com.example.concu.campaign.application.CampaignQueryService;
import com.example.concu.campaign.domain.repository.CampaignRepository;
import com.example.concu.campaign.domain.model.Campaign;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class CampaignQueryServiceImpl implements CampaignQueryService {
    private final CampaignRepository campaignRepository;


    @Override
    public Campaign getCampaignById(Long campaignId) throws Exception {
        Optional<Campaign> campaignOptional = campaignRepository.findById(campaignId);
        campaignOptional.orElseThrow(() -> new Exception("존재하지 않는 캠페인입니다."));
        return campaignOptional.get();
    }

}
