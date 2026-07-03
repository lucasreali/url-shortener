package com.example.urlshotener.application.port.in;

import com.example.urlshotener.domain.model.ShortUrl;

public interface ShortenUrlUseCase {
    ShortUrl shorten(String originalUrl);
}
