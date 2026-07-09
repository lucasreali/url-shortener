package com.example.urlshortener.application.exception;

import com.example.urlshortener.domain.model.Email;

public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException(Email email) {
        super("Email already in use: " + email.value());
    }
}
