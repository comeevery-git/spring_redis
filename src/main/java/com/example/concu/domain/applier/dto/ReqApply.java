package com.example.concu.domain.applier.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class ReqApply {
    private Long campaignId;
    private Long memberId;
    private String name;
    private Long applierMemberId;

}
