package com.example.urlshortener.application.port.out;

import com.example.urlshortener.domain.model.OriginalUrl;
import com.example.urlshortener.domain.model.ShortCode;

import java.util.Optional;

public interface UrlCache {
    Optional<OriginalUrl> find(ShortCode shortCode);
    void put(ShortCode shortCode, OriginalUrl originalUrl);
}
