package com.example.concu.domain.campaign.service.impl;

import com.example.concu.domain.campaign.dto.CampaignInfo;
import com.example.concu.infrastructure.campaign.entity.Campaign;
import com.example.concu.infrastructure.campaign.enums.CampaignStatus;
import com.example.concu.infrastructure.campaign.repository.CampaignRepository;
import com.example.concu.domain.campaign.service.CampaignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class CampaignServiceImpl implements CampaignService {
    private final CampaignRepository campaignRepository;


    /**
     * 캠페인 목록 조회
     * @return
     */
    @Override
    public List<CampaignInfo> getCampaigns() {
        List<Campaign> campaigns = campaignRepository.findAll();
        log.info("campaigns: {}", campaigns);

        List<CampaignInfo> campaignInfos = new ArrayList<>();
        campaigns.forEach(campaign -> {
            CampaignInfo campaignInfo = CampaignInfo.builder()
                    .campaignId(campaign.getCampaignId())
                    .maxApplierCount(campaign.getMaxApplierCount())
                    .build();
            campaignInfos.add(campaignInfo);
        });

        return campaignInfos;
    }

    /**
     * 캠페인 단건 조회
     * @param campaignId
     * @return
     * @throws Exception
     */
    @Override
    public CampaignInfo getCampaignByCampaignId(Long campaignId) throws Exception {
        Optional<Campaign> campaignOptional = campaignRepository.findById(campaignId);
        CampaignInfo campaignInfo = CampaignInfo.builder()
                .campaignId(campaignOptional.get().getCampaignId())
                .maxApplierCount(campaignOptional.get().getMaxApplierCount())
                .build();

        campaignOptional.orElseThrow(() -> new Exception("존재하지 않는 캠페인입니다."));
        return campaignInfo;
    }

    /**
     * H2 캠페인 데이터 생성
     */
    @Override
    public void createCampaigns() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        List<Campaign> campaigns = new ArrayList<>();
        Campaign campaign1 = Campaign.builder()
                .campaignId(1L)
                .title("캠페인1")
                .maxApplierCount(3L)
                .startedAt(now.plusMinutes(1))
                .endedAt(now.plusMinutes(10))
                .campaignStatus(CampaignStatus.READY)
                .build();
        campaigns.add(campaign1);

        Campaign campaign2 = Campaign.builder()
                .campaignId(2L)
                .title("캠페인2")
                .maxApplierCount(10L)
                .startedAt(now)
                .endedAt(now.plusMinutes(2))
                .campaignStatus(CampaignStatus.ACTIVE)
                .build();
        campaigns.add(campaign2);

        Campaign campaign3 = Campaign.builder()
                .campaignId(3L)
                .title("캠페인3")
                .maxApplierCount(50L)
                .startedAt(now)
                .endedAt(now.plusHours(1))
                .campaignStatus(CampaignStatus.READY)
                .build();
        campaigns.add(campaign3);

        Campaign campaign4 = Campaign.builder()
                .campaignId(4L)
                .title("캠페인4")
                .maxApplierCount(100L)
                .startedAt(now)
                .endedAt(now.plusDays(1))
                .campaignStatus(CampaignStatus.READY)
                .build();
        campaigns.add(campaign4);

        campaignRepository.saveAll(campaigns);
    }

}
