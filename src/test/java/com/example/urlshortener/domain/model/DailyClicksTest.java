package com.example.urlshortener.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DailyClicksTest {

    @Test
    void keepsDayAndClicks() {
        DailyClicks dailyClicks = new DailyClicks(LocalDate.parse("2026-07-05"), 42);

        assertEquals(LocalDate.parse("2026-07-05"), dailyClicks.day());
        assertEquals(42, dailyClicks.clicks());
    }

    @Test
    void acceptsZeroClicks() {
        assertEquals(0, new DailyClicks(LocalDate.parse("2026-07-05"), 0).clicks());
    }

    @Test
    void rejectsNullDay() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new DailyClicks(null, 1));
        assertEquals("Day can't be null", exception.getMessage());
    }

    @Test
    void rejectsNegativeClicks() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new DailyClicks(LocalDate.parse("2026-07-05"), -1));
        assertEquals("Clicks can't be negative", exception.getMessage());
    }
}
