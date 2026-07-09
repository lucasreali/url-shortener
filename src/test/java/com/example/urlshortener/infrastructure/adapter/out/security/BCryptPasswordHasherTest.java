package com.example.urlshortener.infrastructure.adapter.out.security;

import com.example.urlshortener.domain.model.PasswordHash;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BCryptPasswordHasherTest {

    private final BCryptPasswordHasher hasher = new BCryptPasswordHasher();

    @Test
    void hashesWithoutExposingRawPassword() {
        PasswordHash hash = hasher.hash("123456");

        assertNotEquals("123456", hash.value());
    }

    @Test
    void matchesCorrectPassword() {
        PasswordHash hash = hasher.hash("123456");

        assertTrue(hasher.matches("123456", hash));
    }

    @Test
    void rejectsWrongPassword() {
        PasswordHash hash = hasher.hash("123456");

        assertFalse(hasher.matches("wrong", hash));
    }
}
