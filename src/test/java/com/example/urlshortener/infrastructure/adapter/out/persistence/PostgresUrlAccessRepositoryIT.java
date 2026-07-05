package com.example.urlshortener.infrastructure.adapter.out.persistence;

import com.example.urlshortener.IntegrationTest;
import com.example.urlshortener.domain.model.DailyClickCounts;
import com.example.urlshortener.domain.model.DailyClicks;
import com.example.urlshortener.domain.model.ShortCode;
import com.example.urlshortener.domain.model.UrlAccess;
import com.example.urlshortener.domain.model.UuidV7;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PostgresUrlAccessRepositoryIT extends IntegrationTest {

    @Autowired
    private PostgresUrlAccessRepository repository;

    @Test
    void groupsClicksByUtcDayInAscendingOrder() {
        ShortCode shortCode = new ShortCode("statsit1");
        repository.add(accessAt(shortCode, "2026-07-03T10:00:00Z"));
        repository.add(accessAt(shortCode, "2026-07-03T23:30:00Z"));
        repository.add(accessAt(shortCode, "2026-07-04T00:15:00Z"));

        DailyClickCounts clicksPerDay = repository.countPerDay(shortCode);

        List<DailyClicks> expected = List.of(
                new DailyClicks(LocalDate.parse("2026-07-03"), 2),
                new DailyClicks(LocalDate.parse("2026-07-04"), 1));
        assertEquals(new DailyClickCounts(expected), clicksPerDay);
        assertEquals(3, clicksPerDay.total());
    }

    @Test
    void returnsNoDaysForShortCodeWithoutAccesses() {
        DailyClickCounts clicksPerDay = repository.countPerDay(new ShortCode("statsit0"));

        assertEquals(0, clicksPerDay.total());
        assertEquals(List.of(), clicksPerDay.days());
    }

    private UrlAccess accessAt(ShortCode shortCode, String instant) {
        return new UrlAccess(UuidV7.generate(), shortCode, Instant.parse(instant));
    }
}
