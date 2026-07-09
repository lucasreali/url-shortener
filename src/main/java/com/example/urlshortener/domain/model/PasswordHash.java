package com.example.urlshortener.domain.model;

public record PasswordHash(String value) {
    public PasswordHash {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Password hash can't be blank");
        }
    }
}
