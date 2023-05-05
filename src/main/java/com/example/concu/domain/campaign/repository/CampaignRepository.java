package com.example.concu.domain.campaign.repository;

import com.example.concu.domain.campaign.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface CampaignRepository extends JpaRepository<Campaign, Long> {


}
