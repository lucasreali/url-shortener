package com.example.urlshortener.domain.model;

import java.util.Locale;
import java.util.regex.Pattern;

public record Email(String value) {
    private static final Pattern FORMAT = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    public Email {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Email can't be blank");
        }

        if (!FORMAT.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid email: " + value);
        }

        value = value.toLowerCase(Locale.ROOT);
    }
}
