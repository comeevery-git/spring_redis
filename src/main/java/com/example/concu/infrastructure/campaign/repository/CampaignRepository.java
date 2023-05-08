package com.example.concu.infrastructure.campaign.repository;

import com.example.concu.infrastructure.campaign.entity.Campaign;
import com.example.concu.infrastructure.campaign.enums.CampaignStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    List<Campaign> findAllByCampaignStatusNot(CampaignStatus deleted);
    Optional<Campaign> findByCampaignIdAndCampaignStatus(Long campaignId, CampaignStatus active);

}
