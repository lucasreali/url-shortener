package com.example.urlshortener.application.port.out;

import com.example.urlshortener.domain.model.ShortCode;
import com.example.urlshortener.domain.model.ShortUrl;

import java.util.Optional;

public interface ShortUrlRepository {
    ShortUrl add(ShortUrl shortUrl);
    Optional<ShortUrl> find(ShortCode shortCode);
}
