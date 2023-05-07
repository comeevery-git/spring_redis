package com.example.concu.infrastructure.member.entity;


import lombok.*;

import javax.persistence.*;

@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    Long memberId;
    @Column
    String name;

}