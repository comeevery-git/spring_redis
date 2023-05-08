package com.example.concu.infrastructure.applier.entity;

import com.example.concu.infrastructure.applier.enums.ApplierUserStatus;
import com.example.concu.infrastructure.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "applier_user")
public class ApplierUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "applier_user_id")
    private Long applierUserId;

    @NotNull
    @Column(name = "campaign_id")
    private Long campaignId;

    @NotNull
    @Column(name = "member_id")
    private Long memberId;

    @NotNull
    @Column(name = "applier_user_status", length = 20)
    @Enumerated(EnumType.STRING)
    private ApplierUserStatus applierUserStatus;

}
