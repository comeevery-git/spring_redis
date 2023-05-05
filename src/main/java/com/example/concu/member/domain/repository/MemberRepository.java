package com.example.concu.member.domain.repository;

import com.example.concu.member.domain.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface MemberRepository extends JpaRepository<Member, Long> {


}
