package com.example.urlshotener.domain.model;

import java.time.Instant;
import java.util.UUID;

public class ShortURL {
    private final UUID id;
    private final ShortCode shortCode;
    private final OriginalURL originalURL;
    private final Instant createdAt;

    public ShortURL(UUID id, ShortCode shortCode, OriginalURL originalURL, Instant createdAt) {
        if (id == null) throw new IllegalArgumentException("Id can't be null");
        if (shortCode == null) throw new IllegalArgumentException("Short code can't be null");
        if (originalURL == null) throw new IllegalArgumentException("Original URL can't be null");
        if (createdAt == null) throw new IllegalArgumentException("Created at can't be null");

        this.id = id;
        this.shortCode = shortCode;
        this.originalURL = originalURL;
        this.createdAt = createdAt;
    }
}
