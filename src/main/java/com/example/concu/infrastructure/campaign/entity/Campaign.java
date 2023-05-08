package com.example.concu.infrastructure.campaign.entity;

import com.example.concu.infrastructure.campaign.enums.CampaignStatus;
import com.example.concu.infrastructure.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "campaign")
public class Campaign extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "campaign_id")
    private Long campaignId;
    @NotNull
    @Column
    private String title;
    @NotNull
    @PositiveOrZero
    @Column(name = "max_applier_count")
    private Long maxApplierCount;
    @NotNull
    @Column(name = "campaign_status", length = 20)
    @Enumerated(EnumType.STRING)
    private CampaignStatus campaignStatus;
    @Column(name = "started_at")
    private LocalDateTime startedAt;
    @Column(name = "ended_at")
    private LocalDateTime endedAt;

}
