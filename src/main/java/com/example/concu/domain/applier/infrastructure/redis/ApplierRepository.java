package com.example.concu.domain.applier.infrastructure.redis;

import com.example.concu.domain.applier.entity.Applier;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;


@EnableRedisRepositories
public interface ApplierRepository extends CrudRepository<Applier, Long> {

}
