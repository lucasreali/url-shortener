package com.example.urlshortener.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OriginalUrlTest {

    @Test
    void acceptsAbsoluteHttpUrl() {
        assertEquals("https://example.com/page", new OriginalUrl("https://example.com/page").url());
    }

    @Test
    void rejectsNullUrl() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new OriginalUrl(null));
        assertEquals("URL can't be blank", exception.getMessage());
    }

    @Test
    void rejectsBlankUrl() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new OriginalUrl("   "));
        assertEquals("URL can't be blank", exception.getMessage());
    }

    @Test
    void rejectsSyntacticallyInvalidUri() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new OriginalUrl("http://exa mple.com"));
        assertEquals("Invalid URL: http://exa mple.com", exception.getMessage());
    }

    @Test
    void rejectsUriWithUnknownProtocol() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new OriginalUrl("foo://example.com"));
        assertEquals("Invalid URL: foo://example.com", exception.getMessage());
    }

    @Test
    void rejectsRelativeUri() {
        // URI.toURL lança IllegalArgumentException direto para URI relativa (não é
        // capturada pelo multi-catch); o handler web ainda a converte em 400.
        assertThrows(IllegalArgumentException.class, () -> new OriginalUrl("example.com"));
    }
}
