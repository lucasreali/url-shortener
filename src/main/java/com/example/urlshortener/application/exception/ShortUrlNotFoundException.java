package com.example.urlshortener.application.exception;

import com.example.urlshortener.domain.model.ShortCode;

public class ShortUrlNotFoundException extends RuntimeException {
    public ShortUrlNotFoundException(ShortCode shortCode) {
        super("Short URL not found for code: " + shortCode);
    }
}
