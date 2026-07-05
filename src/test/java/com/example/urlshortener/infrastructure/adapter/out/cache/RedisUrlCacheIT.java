package com.example.urlshortener.infrastructure.adapter.out.cache;

import com.example.urlshortener.IntegrationTest;
import com.example.urlshortener.domain.model.OriginalUrl;
import com.example.urlshortener.domain.model.ShortCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RedisUrlCacheIT extends IntegrationTest {

    @Autowired
    private RedisUrlCache cache;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void storesAndReadsBackOriginalUrl() {
        ShortCode shortCode = new ShortCode("cacheit1");
        OriginalUrl originalUrl = new OriginalUrl("https://example.com/cached");

        cache.put(shortCode, originalUrl);

        assertEquals(Optional.of(originalUrl), cache.find(shortCode));
    }

    @Test
    void expiresEntriesWithinTwentyFourHours() {
        ShortCode shortCode = new ShortCode("cacheit2");

        cache.put(shortCode, new OriginalUrl("https://example.com/expiring"));

        Long timeToLiveSeconds = redisTemplate.getExpire("url:cacheit2");
        assertTrue(timeToLiveSeconds > 0 && timeToLiveSeconds <= 24 * 60 * 60);
    }

    @Test
    void findsNothingForUnknownShortCode() {
        assertEquals(Optional.empty(), cache.find(new ShortCode("cacheit0")));
    }
}
