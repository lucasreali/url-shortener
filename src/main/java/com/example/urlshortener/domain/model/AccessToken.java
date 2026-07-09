package com.example.urlshortener.domain.model;

public record AccessToken(String value) {
    public AccessToken {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Access token can't be blank");
        }
    }
}
