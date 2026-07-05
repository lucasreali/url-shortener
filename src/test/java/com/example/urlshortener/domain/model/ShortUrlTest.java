package com.example.urlshortener.domain.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShortUrlTest {

    private static final ShortCode SHORT_CODE = new ShortCode("abc123");
    private static final OriginalUrl ORIGINAL_URL = new OriginalUrl("https://example.com");

    @Test
    void keepsAllComponents() {
        UUID id = UuidV7.generate();
        Instant createdAt = Instant.parse("2026-07-05T12:00:00Z");

        ShortUrl shortUrl = new ShortUrl(id, SHORT_CODE, ORIGINAL_URL, createdAt);

        assertEquals(id, shortUrl.id());
        assertEquals(SHORT_CODE, shortUrl.shortCode());
        assertEquals(ORIGINAL_URL, shortUrl.originalUrl());
        assertEquals(createdAt, shortUrl.createdAt());
    }

    @Test
    void rejectsNullId() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new ShortUrl(null, SHORT_CODE, ORIGINAL_URL, Instant.now()));
        assertEquals("Id can't be null", exception.getMessage());
    }

    @Test
    void rejectsNullShortCode() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new ShortUrl(UuidV7.generate(), null, ORIGINAL_URL, Instant.now()));
        assertEquals("Short code can't be null", exception.getMessage());
    }

    @Test
    void rejectsNullOriginalUrl() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new ShortUrl(UuidV7.generate(), SHORT_CODE, null, Instant.now()));
        assertEquals("Original URL can't be null", exception.getMessage());
    }

    @Test
    void rejectsNullCreatedAt() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new ShortUrl(UuidV7.generate(), SHORT_CODE, ORIGINAL_URL, null));
        assertEquals("Created at can't be null", exception.getMessage());
    }

    @Test
    void recordFactoryFillsIdAndCreationTime() {
        Instant before = Instant.now();

        ShortUrl shortUrl = ShortUrl.record(SHORT_CODE, ORIGINAL_URL);

        assertNotNull(shortUrl.id());
        assertEquals(7, shortUrl.id().version());
        assertEquals(SHORT_CODE, shortUrl.shortCode());
        assertEquals(ORIGINAL_URL, shortUrl.originalUrl());
        assertFalse(shortUrl.createdAt().isBefore(before));
    }
}
