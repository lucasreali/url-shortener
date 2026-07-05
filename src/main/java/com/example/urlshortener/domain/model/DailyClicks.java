package com.example.urlshortener.domain.model;

import java.time.LocalDate;

public record DailyClicks(LocalDate day, long clicks) {

    public DailyClicks {
        if (day == null) {
            throw new IllegalArgumentException("Day can't be null");
        }

        if (clicks < 0) {
            throw new IllegalArgumentException("Clicks can't be negative");
        }
    }
}
