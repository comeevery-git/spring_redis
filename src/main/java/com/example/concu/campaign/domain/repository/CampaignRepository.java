package com.example.concu.campaign.domain.repository;

import com.example.concu.campaign.domain.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface CampaignRepository extends JpaRepository<Campaign, Long> {


}
