package com.example.urlshortener.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccessTokenTest {

    @Test
    void keepsTokenValue() {
        assertEquals("token123", new AccessToken("token123").value());
    }

    @Test
    void rejectsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new AccessToken(null));
        assertEquals("Access token can't be blank", exception.getMessage());
    }

    @Test
    void rejectsBlank() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new AccessToken("  "));
        assertEquals("Access token can't be blank", exception.getMessage());
    }
}
