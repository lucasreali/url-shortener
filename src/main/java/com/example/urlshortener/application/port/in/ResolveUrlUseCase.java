package com.example.urlshortener.application.port.in;

import com.example.urlshortener.domain.model.OriginalUrl;

public interface ResolveUrlUseCase {
    OriginalUrl resolve(String shortCode);
}
