package com.example.urlshotener.infrastructure.adapter.out.persistence;

import com.example.urlshotener.domain.model.OriginalUrl;
import com.example.urlshotener.domain.model.ShortCode;
import com.example.urlshotener.domain.model.ShortUrl;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "short_urls")
public class ShortUrlJpaEntity {

    @Id
    private UUID id;

    @Column(name = "short_code", nullable = false, unique = true)
    private String shortCode;

    @Column(name = "original_url", nullable = false)
    private String originalUrl;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected ShortUrlJpaEntity() {
    }

    public static ShortUrlJpaEntity fromDomain(ShortUrl shortUrl) {
        ShortUrlJpaEntity entity = new ShortUrlJpaEntity();
        entity.id = shortUrl.id();
        entity.shortCode = shortUrl.shortCode().value();
        entity.originalUrl = shortUrl.originalUrl().url();
        entity.createdAt = shortUrl.createdAt();

        return entity;
    }

    public ShortUrl toDomain() {
        return new ShortUrl(id, new ShortCode(shortCode), new OriginalUrl(originalUrl), createdAt);
    }
}
