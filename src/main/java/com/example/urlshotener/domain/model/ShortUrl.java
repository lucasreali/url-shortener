package com.example.urlshotener.domain.model;

import java.time.Instant;
import java.util.UUID;

public class ShortUrl {
    private final UUID id;
    private final ShortCode shortCode;
    private final OriginalUrl originalUrl;
    private final Instant createdAt;

    public ShortUrl(UUID id, ShortCode shortCode, OriginalUrl originalUrl, Instant createdAt) {

        if (id == null) throw new IllegalArgumentException("Id can't be null");
        if (shortCode == null) throw new IllegalArgumentException("Short code can't be null");
        if (originalUrl == null) throw new IllegalArgumentException("Original URL can't be null");
        if (createdAt == null) throw new IllegalArgumentException("Created at can't be null");

        this.id = id;
        this.shortCode = shortCode;
        this.originalUrl = originalUrl;
        this.createdAt = createdAt;
    }
}
