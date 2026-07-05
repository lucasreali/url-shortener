package com.example.urlshortener.domain.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UrlAccessTest {

    @Test
    void recordFactoryFillsIdAndAccessTime() {
        ShortCode shortCode = new ShortCode("abc123");
        Instant before = Instant.now();

        UrlAccess urlAccess = UrlAccess.record(shortCode);

        assertNotNull(urlAccess.id());
        assertEquals(7, urlAccess.id().version());
        assertEquals(shortCode, urlAccess.shortCode());
        assertFalse(urlAccess.accessedAt().isBefore(before));
    }
}
