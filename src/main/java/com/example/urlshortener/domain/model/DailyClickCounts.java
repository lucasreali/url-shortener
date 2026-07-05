package com.example.urlshortener.domain.model;

import java.util.List;

public record DailyClickCounts(List<DailyClicks> days) {

    public DailyClickCounts {
        if (days == null) {
            throw new IllegalArgumentException("Days can't be null");
        }

        days = List.copyOf(days);
    }

    public long total() {
        return days.stream().mapToLong(DailyClicks::clicks).sum();
    }
}
