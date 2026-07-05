package com.example.urlshortener.application.service;

import com.example.urlshortener.application.port.in.ShortenUrlUseCase;
import com.example.urlshortener.application.port.out.ShortCodeGenerator;
import com.example.urlshortener.application.port.out.ShortUrlRepository;
import com.example.urlshortener.domain.model.OriginalUrl;
import com.example.urlshortener.domain.model.ShortCode;
import com.example.urlshortener.domain.model.ShortUrl;
import org.springframework.stereotype.Service;

@Service
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

        ShortUrl shortUrl = ShortUrl.record(shortCode, original);

        return this.repository.add(shortUrl);
    }
}
