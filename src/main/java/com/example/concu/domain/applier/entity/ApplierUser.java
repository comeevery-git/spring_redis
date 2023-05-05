package com.example.concu.domain.applier.entity;

import javax.persistence.*;

@Entity
@Table(name = "applier_user")
public class ApplierUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "applier_user_id")
    private Long applierUserId;

}
