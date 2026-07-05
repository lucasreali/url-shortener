package com.example.urlshortener.domain.model;

import java.time.Instant;
import java.util.UUID;

public record ShortUrl(UUID id, ShortCode shortCode, OriginalUrl originalUrl, Instant createdAt) {
    public ShortUrl {
        if (id == null) throw new IllegalArgumentException("Id can't be null");
        if (shortCode == null) throw new IllegalArgumentException("Short code can't be null");
        if (originalUrl == null) throw new IllegalArgumentException("Original URL can't be null");
        if (createdAt == null) throw new IllegalArgumentException("Created at can't be null");
    }
}
