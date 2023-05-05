package com.example.concu.applier.domain.model;

import javax.persistence.*;

@Entity
@Table(name = "applier_user")
public class ApplierUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "applier_user_id")
    private Long applierUserId;

}
