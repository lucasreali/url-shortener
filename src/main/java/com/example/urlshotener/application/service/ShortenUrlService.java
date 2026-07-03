package com.example.urlshotener.application.service;

import com.example.urlshotener.application.port.in.ShortenUrlUseCase;
import com.example.urlshotener.application.port.out.ShortUrlRepository;
import com.example.urlshotener.domain.model.OriginalUrl;
import com.example.urlshotener.domain.model.ShortCode;
import com.example.urlshotener.domain.model.ShortUrl;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.UUID;

public class ShortenUrlService implements ShortenUrlUseCase {
    private final ShortUrlRepository repository;

    public ShortenUrlService(ShortUrlRepository repository) {
        this.repository = repository;
    }

    @Override
    public ShortUrl shorten(String originalUrl) {
        OriginalUrl original = new OriginalUrl(originalUrl);

        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        SecureRandom random = new SecureRandom();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characters.length());
            char sorted = characters.charAt(index);
            builder.append(sorted);
        }

        String code = builder.toString();
        ShortCode shortCode = new ShortCode(code);
        UUID id = UUID.randomUUID();
        Instant createdAt = Instant.now();

        ShortUrl shortUrl = new ShortUrl(id, shortCode, original, createdAt);

        return this.repository.add(shortUrl);
    }
}
