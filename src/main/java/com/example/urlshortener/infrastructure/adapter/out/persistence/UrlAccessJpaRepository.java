package com.example.urlshortener.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UrlAccessJpaRepository extends JpaRepository<UrlAccessJpaEntity, UUID> {
    long countByShortCode(String shortCode);
}
