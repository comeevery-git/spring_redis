package com.example.concu.application.service.impl;

import com.example.concu.application.service.CampaignApplicationService;
import com.example.concu.infrastructure.campaign.enums.CampaignStatus;
import com.example.concu.infrastructure.campaign.repository.CampaignRepository;
import com.example.concu.infrastructure.campaign.entity.Campaign;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class CampaignApplicationServiceImpl implements CampaignApplicationService {
    private final CampaignRepository campaignRepository;


    @Override
    public Campaign getCampaignByIdAndActive(Long campaignId) throws Exception {
        Optional<Campaign> campaignOptional = campaignRepository.findByCampaignIdAndCampaignStatus(campaignId, CampaignStatus.ACTIVE);
        campaignOptional.orElseThrow(() -> new Exception("활성화 된 캠페인이 존재하지 않습니다."));
        return campaignOptional.get();
    }

}
