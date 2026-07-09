package com.example.urlshortener.domain.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

    private static final Email EMAIL = new Email("lucas@example.com");
    private static final PasswordHash PASSWORD_HASH = new PasswordHash("$2a$10$hash");

    @Test
    void keepsAllComponents() {
        UUID id = UuidV7.generate();

        User user = new User(id, EMAIL, PASSWORD_HASH);

        assertEquals(id, user.id());
        assertEquals(EMAIL, user.email());
        assertEquals(PASSWORD_HASH, user.passwordHash());
    }

    @Test
    void rejectsNullId() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new User(null, EMAIL, PASSWORD_HASH));
        assertEquals("Id can't be null", exception.getMessage());
    }

    @Test
    void rejectsNullEmail() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new User(UuidV7.generate(), null, PASSWORD_HASH));
        assertEquals("Email can't be null", exception.getMessage());
    }

    @Test
    void rejectsNullPasswordHash() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new User(UuidV7.generate(), EMAIL, null));
        assertEquals("Password hash can't be null", exception.getMessage());
    }

    @Test
    void registerFactoryFillsId() {
        User user = User.register(EMAIL, PASSWORD_HASH);

        assertNotNull(user.id());
        assertEquals(7, user.id().version());
        assertEquals(EMAIL, user.email());
        assertEquals(PASSWORD_HASH, user.passwordHash());
    }
}
