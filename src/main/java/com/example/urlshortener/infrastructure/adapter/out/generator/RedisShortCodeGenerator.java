package com.example.urlshortener.infrastructure.adapter.out.generator;

import com.example.urlshortener.application.port.out.ShortCodeGenerator;
import com.example.urlshortener.domain.model.ShortCode;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisShortCodeGenerator implements ShortCodeGenerator {

    private final StringRedisTemplate redisTemplate;
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public RedisShortCodeGenerator(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public ShortCode generate() {
        Long sequence = redisTemplate.opsForValue().increment("short_url:counter");
        return new ShortCode(encodeBase62(sequence + 916_132_832L));
    }

    private String encodeBase62(long number) {
        StringBuilder encoded = new StringBuilder();
        while (number > 0) {
            int remainder = (int) (number % 62);
            encoded.insert(0, ALPHABET.charAt(remainder));
            number = number / 62;
        }

        return encoded.toString();
    }
}
