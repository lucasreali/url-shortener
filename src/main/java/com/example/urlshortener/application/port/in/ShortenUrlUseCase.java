package com.example.urlshortener.application.port.in;

import com.example.urlshortener.domain.model.ShortUrl;

public interface ShortenUrlUseCase {
    ShortUrl shorten(String originalUrl);
}
