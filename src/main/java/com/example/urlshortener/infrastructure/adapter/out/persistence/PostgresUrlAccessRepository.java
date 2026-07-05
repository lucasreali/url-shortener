package com.example.urlshortener.infrastructure.adapter.out.persistence;

import com.example.urlshortener.application.port.out.UrlAccessRepository;
import com.example.urlshortener.domain.model.ShortCode;
import com.example.urlshortener.domain.model.UrlAccess;
import org.springframework.stereotype.Component;

@Component
public class PostgresUrlAccessRepository implements UrlAccessRepository {

    private final UrlAccessJpaRepository jpaRepository;

    public PostgresUrlAccessRepository(UrlAccessJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void add(UrlAccess urlAccess) {
        jpaRepository.save(UrlAccessJpaEntity.fromDomain(urlAccess));
    }

    @Override
    public long countBy(ShortCode shortCode) {
        return jpaRepository.countByShortCode(shortCode.value());
    }
}
