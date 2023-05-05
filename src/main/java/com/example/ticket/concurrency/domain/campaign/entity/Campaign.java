package com.example.ticket.concurrency.domain.campaign.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "campaign")
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "campaign_id")
    Long campaignId;
    @Column(name = "max_applier_count")
    Long maxApplierCount;

}
