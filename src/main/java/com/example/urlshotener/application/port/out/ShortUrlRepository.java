package com.example.urlshotener.application.port.out;

import com.example.urlshotener.domain.model.ShortUrl;

public interface ShortUrlRepository {
    ShortUrl add(ShortUrl shortUrl);
}
