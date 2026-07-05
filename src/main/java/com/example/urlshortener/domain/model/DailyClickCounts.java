package com.example.urlshortener.domain.model;

import java.util.List;
import java.util.function.Function;

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

    public <T> List<T> mapEach(Function<DailyClicks, T> mapper) {
        return days.stream().map(mapper).toList();
    }
}
