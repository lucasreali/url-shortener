package com.example.urlshortener.infrastructure.adapter.out.cache;

import com.example.urlshortener.application.port.out.UrlCache;
import com.example.urlshortener.domain.model.OriginalUrl;
import com.example.urlshortener.domain.model.ShortCode;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Component
public class RedisUrlCache implements UrlCache {
    private final StringRedisTemplate redisTemplate;

    public RedisUrlCache(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Optional<OriginalUrl> find(ShortCode shortCode) {
        String url = redisTemplate.opsForValue().get("url:" + shortCode.value());
        return Optional.ofNullable(url).map(OriginalUrl::new);
    }

    @Override
    public void put(ShortCode shortCode, OriginalUrl originalUrl) {
        redisTemplate.opsForValue().set("url:" + shortCode.value(), originalUrl.url(), Duration.ofHours(24));
    }
}
