package com.example.urlshortener.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DailyClickCountsTest {

    @Test
    void sumsClicksAcrossDays() {
        DailyClickCounts counts = new DailyClickCounts(List.of(
                new DailyClicks(LocalDate.parse("2026-07-04"), 40),
                new DailyClicks(LocalDate.parse("2026-07-05"), 2)));

        assertEquals(42, counts.total());
    }

    @Test
    void totalIsZeroWithoutDays() {
        assertEquals(0, new DailyClickCounts(List.of()).total());
    }

    @Test
    void rejectsNullDays() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new DailyClickCounts(null));
        assertEquals("Days can't be null", exception.getMessage());
    }

    @Test
    void copiesDaysSoLaterMutationOfSourceIsIgnored() {
        List<DailyClicks> source = new ArrayList<>();
        source.add(new DailyClicks(LocalDate.parse("2026-07-05"), 1));
        DailyClickCounts counts = new DailyClickCounts(source);

        source.add(new DailyClicks(LocalDate.parse("2026-07-06"), 1));

        assertEquals(1, counts.total());
        assertThrows(UnsupportedOperationException.class,
                () -> counts.days().add(new DailyClicks(LocalDate.parse("2026-07-07"), 1)));
    }
}
