package com.example.urlshortener.domain.model;

import java.util.UUID;

public record User(UUID id, Email email, PasswordHash passwordHash) {
    public User {
        if (id == null) throw new IllegalArgumentException("Id can't be null");
        if (email == null) throw new IllegalArgumentException("Email can't be null");
        if (passwordHash == null) throw new IllegalArgumentException("Password hash can't be null");
    }

    public static User register(Email email, PasswordHash passwordHash) {
        return new User(UuidV7.generate(), email, passwordHash);
    }
}
