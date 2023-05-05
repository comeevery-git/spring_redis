package com.example.ticket.concurrency.domain.member.repository;

import com.example.ticket.concurrency.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface MemberRepository extends JpaRepository<Member, Long> {


}
