package com.example.concu.infrastructure.common.redis;

import com.example.concu.infrastructure.applier.entity.Applier;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;


@EnableRedisRepositories
public interface ApplierRepository extends CrudRepository<Applier, Long> {

}
