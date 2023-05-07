package com.example.concu.infrastructure.applier.repository;

import com.example.concu.infrastructure.applier.entity.ApplierUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface ApplierUserRepository extends JpaRepository<ApplierUser, Long> {

}
