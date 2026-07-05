package com.example.urlshortener.infrastructure.adapter.out.generator;

import com.example.urlshortener.application.port.out.ShortCodeGenerator;
import com.example.urlshortener.domain.model.ShortCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.sqids.Sqids;

import java.util.List;

@Component
public class RedisShortCodeGenerator implements ShortCodeGenerator {

    private final StringRedisTemplate redisTemplate;
    private final Sqids sqids;

    public RedisShortCodeGenerator(
            StringRedisTemplate redisTemplate,
            @Value("${app.short-code.alphabet}") String alphabet) {
        this.redisTemplate = redisTemplate;
        this.sqids = Sqids.builder()
                .alphabet(alphabet)
                .minLength(6)
                .build();
    }
    @Override
    public ShortCode generate() {
        Long sequence = redisTemplate.opsForValue().increment("short_url:counter");
        return new ShortCode(sqids.encode(List.of(sequence)));
    }
}
