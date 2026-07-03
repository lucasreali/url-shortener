package com.example.urlshotener.application.service;

import com.example.urlshotener.application.port.in.ShortenUrlUseCase;
import com.example.urlshotener.application.port.out.ShortUrlRepository;
import com.example.urlshotener.domain.model.ShortUrl;

public class ShortenUrlService implements ShortenUrlUseCase {
    private final ShortUrlRepository repository;

    public ShortenUrlService(ShortUrlRepository repository) {
        this.repository = repository;
    }

    @Override
    public ShortUrl shorten(String originalUrl) {
        return null;
    }
}
