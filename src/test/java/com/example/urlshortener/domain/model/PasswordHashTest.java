package com.example.urlshortener.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PasswordHashTest {

    @Test
    void keepsHashValue() {
        assertEquals("$2a$10$hash", new PasswordHash("$2a$10$hash").value());
    }

    @Test
    void rejectsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new PasswordHash(null));
        assertEquals("Password hash can't be blank", exception.getMessage());
    }

    @Test
    void rejectsBlank() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new PasswordHash("  "));
        assertEquals("Password hash can't be blank", exception.getMessage());
    }
}
