package com.example.urlshortener.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UrlStatsTest {

    @Test
    void totalClicksComesFromDailyCounts() {
        UrlStats urlStats = new UrlStats(
                new ShortCode("abc123"),
                new OriginalUrl("https://example.com"),
                new DailyClickCounts(List.of(
                        new DailyClicks(LocalDate.parse("2026-07-04"), 40),
                        new DailyClicks(LocalDate.parse("2026-07-05"), 2))));

        assertEquals(42, urlStats.totalClicks());
    }
}
