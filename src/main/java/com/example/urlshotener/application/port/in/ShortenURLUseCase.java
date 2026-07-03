package com.example.urlshotener.application.port.in;

import com.example.urlshotener.domain.model.ShortURL;

public interface ShortenURLUseCase {
    ShortURL shorten(String originalUrl);
}
