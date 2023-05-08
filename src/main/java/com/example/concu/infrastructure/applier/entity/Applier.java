package com.example.concu.infrastructure.applier.entity;

import com.example.concu.infrastructure.applier.enums.ApplierUserStatus;
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
    private ApplierUserStatus applierUserStatus;

    private Long applierMemberId;
}
