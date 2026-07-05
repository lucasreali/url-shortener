package com.example.urlshortener.infrastructure.adapter.out.persistence;

import com.example.urlshortener.application.port.out.ShortUrlRepository;
import com.example.urlshortener.domain.model.ShortCode;
import com.example.urlshortener.domain.model.ShortUrl;

import java.util.HashMap;
import java.util.Optional;

public class InMemoryShortUrlRepository implements ShortUrlRepository {
    private final HashMap<ShortCode, ShortUrl> shortUrlsByCode = new HashMap<ShortCode, ShortUrl>();

    @Override
    public ShortUrl add(ShortUrl shortUrl) {
        shortUrlsByCode.put(shortUrl.shortCode(), shortUrl);
        return shortUrl;
    }

    @Override
    public Optional<ShortUrl> find(ShortCode shortCode) {
        return Optional.ofNullable(shortUrlsByCode.get(shortCode));
    }
}
