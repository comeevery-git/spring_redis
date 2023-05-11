package com.example.concu.application.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

@RequiredArgsConstructor
public class BaseRedisQueue {
    private final RedisTemplate<String, String> redisTemplate;
    private final String queueName;

    public void enqueue(String message, double priority) {
        redisTemplate.opsForZSet().add(queueName, message, priority);
    }
    public void enqueueObj(Object obj, double priority) {
        redisTemplate.opsForZSet().add(queueName, obj.toString(), priority);
    }

    public String dequeue() {
        String message = redisTemplate.opsForZSet().range(queueName, 0, 0).iterator().next();
        redisTemplate.opsForZSet().remove(queueName, message);
        return message;
    }

    public Set<String> rangeAllByKey(String key) {
        return redisTemplate.opsForZSet().range(queueName, 0, redisTemplate.opsForZSet().size(key));
    }

    public Set<String> rangeByKeyAndSize(String key, Long maxApplierCount) {
        return redisTemplate.opsForZSet().range(queueName, 0, maxApplierCount);
    }

    public Long getSize(String key) {
        return redisTemplate.opsForZSet().size(key);
    }


}
