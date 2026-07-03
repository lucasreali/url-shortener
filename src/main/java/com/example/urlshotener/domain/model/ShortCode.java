package com.example.urlshotener.domain.model;

public class ShortCode {
    private final String value;

    public ShortCode(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Short code can't be null");
        }

        if (!value.matches("^[A-Za-z0-9]{6}$")) {
            throw new IllegalArgumentException("Invalid short code: " + value);
        }

        this.value = value;
    }
}
