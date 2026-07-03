package com.example.urlshotener.application.port.out;

import com.example.urlshotener.domain.model.ShortURL;

public interface ShortURLRepository {
    ShortURL add(ShortURL shortURL);
}
