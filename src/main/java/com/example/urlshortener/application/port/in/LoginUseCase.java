package com.example.urlshortener.application.port.in;

import com.example.urlshortener.domain.model.AccessToken;

public interface LoginUseCase {
    AccessToken login(String email, String rawPassword);
}
