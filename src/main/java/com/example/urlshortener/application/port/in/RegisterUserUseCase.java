package com.example.urlshortener.application.port.in;

import com.example.urlshortener.domain.model.User;

public interface RegisterUserUseCase {
    User register(String email, String rawPassword);
}
