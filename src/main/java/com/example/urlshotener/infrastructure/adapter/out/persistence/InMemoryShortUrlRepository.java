package com.example.urlshotener.infrastructure.adapter.out.persistence;

import com.example.urlshotener.application.port.out.ShortUrlRepository;
import com.example.urlshotener.domain.model.ShortCode;
import com.example.urlshotener.domain.model.ShortUrl;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class InMemoryShortUrlRepository implements ShortUrlRepository {
    private final HashMap<ShortCode, ShortUrl> shortUrlsByCode = new HashMap<ShortCode, ShortUrl>();

    @Override
    public ShortUrl add(ShortUrl shortUrl) {
        shortUrlsByCode.put(shortUrl.shortCode(), shortUrl);
        return shortUrl;
    }
}
