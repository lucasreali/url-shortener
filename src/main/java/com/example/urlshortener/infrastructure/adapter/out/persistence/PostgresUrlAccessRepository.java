package com.example.urlshortener.infrastructure.adapter.out.persistence;

import com.example.urlshortener.application.port.out.UrlAccessRepository;
import com.example.urlshortener.domain.model.DailyClickCounts;
import com.example.urlshortener.domain.model.DailyClicks;
import com.example.urlshortener.domain.model.ShortCode;
import com.example.urlshortener.domain.model.UrlAccess;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public DailyClickCounts countPerDay(ShortCode shortCode) {
        List<DailyClicks> days = jpaRepository.countPerDayByShortCode(shortCode.value()).stream()
                .map(this::toDomain)
                .toList();
        return new DailyClickCounts(days);
    }

    private DailyClicks toDomain(DailyClicksProjection projection) {
        return new DailyClicks(projection.getDay(), projection.getClicks());
    }
}
