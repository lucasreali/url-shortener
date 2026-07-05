package com.example.urlshortener.domain.model;

import java.time.Instant;
import java.util.UUID;

public record UrlAccess(UUID id, ShortCode shortCode, Instant accessedAt) {

    public static UrlAccess record(ShortCode shortCode) {
        return new UrlAccess(UuidV7.generate(), shortCode, Instant.now());
    }
}
