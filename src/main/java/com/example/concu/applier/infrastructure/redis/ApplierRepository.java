package com.example.concu.applier.infrastructure.redis;

import com.example.concu.applier.domain.model.Applier;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;


@EnableRedisRepositories
public interface ApplierRepository extends CrudRepository<Applier, Long> {

}
