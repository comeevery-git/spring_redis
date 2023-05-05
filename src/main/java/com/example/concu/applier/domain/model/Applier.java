package com.example.concu.applier.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class Applier {
    private Integer rank;
    private Long campaignId;
    private Long memberId;
    private String name;
    private LocalDateTime applyTime;

    private Long applierMemberId;
}
