package com.example.urlshortener.application.port.out;

import com.example.urlshortener.domain.model.PasswordHash;

public interface PasswordHasher {
    PasswordHash hash(String rawPassword);
    boolean matches(String rawPassword, PasswordHash passwordHash);
}
