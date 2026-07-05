package com.example.urlshotener.infrastructure.adapter.out.persistence;

import com.example.urlshotener.application.port.out.ShortUrlRepository;
import com.example.urlshotener.domain.model.ShortCode;
import com.example.urlshotener.domain.model.ShortUrl;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PostgresShortUrlRepository implements ShortUrlRepository {
    private final ShortUrlJpaRepository jpaRepository;


    public PostgresShortUrlRepository(ShortUrlJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public ShortUrl add(ShortUrl shortUrl) {
        ShortUrlJpaEntity entity = ShortUrlJpaEntity.fromDomain(shortUrl);
        return jpaRepository.save(entity).toDomain();
    }

    @Override
    public Optional<ShortUrl> find(ShortCode shortCode) {
        return jpaRepository.findByShortCode(shortCode.value())
                .map(ShortUrlJpaEntity::toDomain);
    }
}
