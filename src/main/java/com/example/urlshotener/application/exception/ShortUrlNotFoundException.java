package com.example.urlshotener.application.exception;

import com.example.urlshotener.domain.model.ShortCode;

public class ShortUrlNotFoundException extends RuntimeException {
    public ShortUrlNotFoundException(ShortCode shortCode) {
        super("Short URL not found for code: " + shortCode);
    }
}
