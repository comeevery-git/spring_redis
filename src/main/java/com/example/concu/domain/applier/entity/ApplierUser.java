package com.example.concu.domain.applier.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

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
    @Column(name = "applie_user_status", length = 1, columnDefinition = "varchar(1) default 'P'")
    private String applierUserStatus;

}
