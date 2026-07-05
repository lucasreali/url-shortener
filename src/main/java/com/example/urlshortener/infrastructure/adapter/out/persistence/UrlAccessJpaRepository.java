package com.example.urlshortener.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UrlAccessJpaRepository extends JpaRepository<UrlAccessJpaEntity, UUID> {

    @Query(nativeQuery = true, value = """
            SELECT (accessed_at AT TIME ZONE 'UTC')::date AS day, count(*) AS clicks
            FROM url_accesses
            WHERE short_code = :shortCode
            GROUP BY day
            ORDER BY day""")
    List<DailyClicksProjection> countPerDayByShortCode(String shortCode);
}
