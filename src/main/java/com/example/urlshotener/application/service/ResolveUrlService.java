package com.example.urlshotener.application.service;

import com.example.urlshotener.application.exception.ShortUrlNotFoundException;
import com.example.urlshotener.application.port.in.ResolveUrlUseCase;
import com.example.urlshotener.application.port.out.ShortUrlRepository;
import com.example.urlshotener.domain.model.OriginalUrl;
import com.example.urlshotener.domain.model.ShortCode;
import com.example.urlshotener.domain.model.ShortUrl;
import org.springframework.stereotype.Service;


@Service
public class ResolveUrlService implements ResolveUrlUseCase {
    private final ShortUrlRepository repository;


    public ResolveUrlService(ShortUrlRepository repository) {
        this.repository = repository;
    }

    @Override
    public OriginalUrl resolve(String shortCode) {

        ShortCode code = new ShortCode(shortCode);

        return this.repository.find(code)
                .map(ShortUrl::originalUrl)
                .orElseThrow(() -> new ShortUrlNotFoundException(code));
    }
}
