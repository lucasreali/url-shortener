package com.example.urlshortener.domain.model;

public record UrlStats(ShortCode shortCode, OriginalUrl originalUrl, DailyClickCounts clicksPerDay) {

    public long totalClicks() {
        return clicksPerDay.total();
    }
}
