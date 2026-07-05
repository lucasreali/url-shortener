package com.example.urlshortener.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ShortUrlJpaRepository extends JpaRepository<ShortUrlJpaEntity, UUID> {
    Optional<ShortUrlJpaEntity> findByShortCode(String shortCode);
}
