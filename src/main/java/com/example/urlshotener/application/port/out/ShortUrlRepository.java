package com.example.urlshotener.application.port.out;

import com.example.urlshotener.domain.model.ShortCode;
import com.example.urlshotener.domain.model.ShortUrl;

import java.util.Optional;

public interface ShortUrlRepository {
    ShortUrl add(ShortUrl shortUrl);
    Optional<ShortUrl> find(ShortCode shortCode);
}
