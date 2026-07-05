package com.example.urlshortener.infrastructure.adapter.out.persistence;

import com.example.urlshortener.IntegrationTest;
import com.example.urlshortener.domain.model.OriginalUrl;
import com.example.urlshortener.domain.model.ShortCode;
import com.example.urlshortener.domain.model.ShortUrl;
import com.example.urlshortener.domain.model.UuidV7;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PostgresShortUrlRepositoryIT extends IntegrationTest {

    @Autowired
    private PostgresShortUrlRepository repository;

    @Test
    void persistsAndReadsBackShortUrl() {
        // Postgres guarda TIMESTAMPTZ com precisão de microssegundos; truncamos
        // para o roundtrip ser comparável por igualdade.
        ShortUrl shortUrl = new ShortUrl(
                UuidV7.generate(),
                new ShortCode("repoit1"),
                new OriginalUrl("https://example.com/persisted"),
                Instant.now().truncatedTo(ChronoUnit.MICROS));

        ShortUrl saved = repository.add(shortUrl);
        Optional<ShortUrl> found = repository.find(new ShortCode("repoit1"));

        assertEquals(shortUrl, saved);
        assertEquals(Optional.of(shortUrl), found);
    }

    @Test
    void findsNothingForUnknownShortCode() {
        assertEquals(Optional.empty(), repository.find(new ShortCode("repoit0")));
    }
}
