package com.example.urlshortener.application.port.out;

import com.example.urlshortener.domain.model.ShortCode;

public interface UrlAccessPublisher {
    void publish(ShortCode shortCode);
}
