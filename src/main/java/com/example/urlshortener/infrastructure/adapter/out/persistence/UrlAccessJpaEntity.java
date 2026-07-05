package com.example.urlshortener.infrastructure.adapter.out.persistence;


import com.example.urlshortener.domain.model.ShortCode;
import com.example.urlshortener.domain.model.UrlAccess;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "url_accesses")
public class UrlAccessJpaEntity {

    @Id
    private UUID id;

    @Column(name = "short_code", nullable = false)
    private String shortCode;

    @Column(name = "accessed_at", nullable = false)
    private Instant accessedAt;

    protected UrlAccessJpaEntity() {
    }

    public static UrlAccessJpaEntity fromDomain(UrlAccess urlAccess) {
        UrlAccessJpaEntity urlAccessJpaEntity = new UrlAccessJpaEntity();
        urlAccessJpaEntity.id = urlAccess.id();
        urlAccessJpaEntity.shortCode = urlAccess.shortCode().value();
        urlAccessJpaEntity.accessedAt = urlAccess.accessedAt();

        return  urlAccessJpaEntity;
    }

    public UrlAccess toDomain() {
        return new UrlAccess(id, new ShortCode(shortCode), accessedAt);
    }
}
