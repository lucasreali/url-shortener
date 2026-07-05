package com.example.urlshortener.application.port.out;

import com.example.urlshortener.domain.model.ShortCode;

public interface ShortCodeGenerator {
    ShortCode generate();
}
