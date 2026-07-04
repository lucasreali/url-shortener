package com.example.urlshotener.application.service;

import com.example.urlshotener.application.port.in.ShortenUrlUseCase;
import com.example.urlshotener.application.port.out.ShortCodeGenerator;
import com.example.urlshotener.application.port.out.ShortUrlRepository;
import com.example.urlshotener.domain.model.OriginalUrl;
import com.example.urlshotener.domain.model.ShortCode;
import com.example.urlshotener.domain.model.ShortUrl;

import java.time.Instant;
import java.util.UUID;

public class ShortenUrlService implements ShortenUrlUseCase {
    private final ShortUrlRepository repository;
    private final ShortCodeGenerator generator;

    public ShortenUrlService(ShortUrlRepository repository, ShortCodeGenerator generator) {
        this.repository = repository;
        this.generator = generator;
    }

    @Override
    public ShortUrl shorten(String originalUrl) {
        OriginalUrl original = new OriginalUrl(originalUrl);

        ShortCode shortCode = generator.generate();
        UUID id = UUID.randomUUID();
        Instant createdAt = Instant.now();

        ShortUrl shortUrl = new ShortUrl(id, shortCode, original, createdAt);

        return this.repository.add(shortUrl);
    }
}
