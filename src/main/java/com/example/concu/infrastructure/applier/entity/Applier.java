package com.example.concu.infrastructure.applier.entity;

import com.example.concu.infrastructure.applier.enums.ApplierUserStatus;
import com.example.concu.utils.GsonUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Applier implements Serializable {
    private Long score;
    private Long campaignId;
    private Long memberId;
    private String name;
    private LocalDateTime applyTime;
    private ApplierUserStatus applierUserStatus;

    private Long applierMemberId;

    @Override
    public String toString() {
        return GsonUtils.toJson(this);
    }
}
