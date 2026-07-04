package com.example.urlshotener.application.port.in;

import com.example.urlshotener.domain.model.OriginalUrl;

public interface ResolveUrlUseCase {
    OriginalUrl resolve(String shortCode);
}
