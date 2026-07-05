package com.example.urlshortener.application.service;

import com.example.urlshortener.application.exception.ShortUrlNotFoundException;
import com.example.urlshortener.application.port.in.ResolveUrlUseCase;
import com.example.urlshortener.application.port.out.ShortUrlRepository;
import com.example.urlshortener.application.port.out.UrlCache;
import com.example.urlshortener.domain.model.OriginalUrl;
import com.example.urlshortener.domain.model.ShortCode;
import com.example.urlshortener.domain.model.ShortUrl;
import org.springframework.stereotype.Service;


@Service
public class ResolveUrlService implements ResolveUrlUseCase {
    private final ShortUrlRepository repository;
    private final UrlCache cache;


    public ResolveUrlService(ShortUrlRepository repository, UrlCache cache) {
        this.repository = repository;
        this.cache = cache;
    }

    @Override
    public OriginalUrl resolve(String shortCode) {

        ShortCode code = new ShortCode(shortCode);

        return cache.find(code).orElseGet(() -> resolveFromRepository(code));
    }

    private OriginalUrl resolveFromRepository(ShortCode code) {
        OriginalUrl originalUrl = this.repository.find(code)
                .map(ShortUrl::originalUrl)
                .orElseThrow(() -> new ShortUrlNotFoundException(code));
        cache.put(code, originalUrl);
        return originalUrl;
    }
}
