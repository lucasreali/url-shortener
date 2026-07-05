package com.example.urlshortener.domain.model;

import java.time.Instant;
import java.util.UUID;

public record UrlAccess(UUID id, ShortCode shortCode, Instant accessedAt) {

    public static UrlAccess record(ShortCode shortCode) {
        return new UrlAccess(UUID.randomUUID(), shortCode, Instant.now());
    }
}
