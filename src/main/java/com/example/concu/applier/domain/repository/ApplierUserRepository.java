package com.example.concu.applier.domain.repository;

import com.example.concu.applier.domain.model.ApplierUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface ApplierUserRepository extends JpaRepository<ApplierUser, Long> {

}
