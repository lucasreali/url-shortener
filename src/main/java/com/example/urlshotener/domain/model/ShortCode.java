package com.example.urlshotener.domain.model;

public record ShortCode(String value) {

    public ShortCode {
        if (value == null) {
            throw new IllegalArgumentException("Short code can't be null");
        }

        if (!value.matches("^[A-Za-z0-9]{6}$")) {
            throw new IllegalArgumentException("Invalid short code: " + value);
        }
    }
}
