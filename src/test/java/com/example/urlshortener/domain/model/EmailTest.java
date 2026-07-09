package com.example.urlshortener.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailTest {

    @Test
    void keepsValidEmail() {
        assertEquals("lucas@example.com", new Email("lucas@example.com").value());
    }

    @Test
    void normalizesToLowerCase() {
        assertEquals("lucas@example.com", new Email("Lucas@Example.COM").value());
    }

    @Test
    void rejectsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Email(null));
        assertEquals("Email can't be blank", exception.getMessage());
    }

    @Test
    void rejectsBlank() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Email("  "));
        assertEquals("Email can't be blank", exception.getMessage());
    }

    @Test
    void rejectsMalformedEmail() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Email("not-an-email"));
        assertEquals("Invalid email: not-an-email", exception.getMessage());
    }
}
