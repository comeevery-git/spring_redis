package com.example.ticket.concurrency.domain.applier.repository.redis;

import com.example.ticket.concurrency.domain.applier.entity.Applier;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;


@EnableRedisRepositories
public interface ApplierRepository extends CrudRepository<Applier, Long> {

}
