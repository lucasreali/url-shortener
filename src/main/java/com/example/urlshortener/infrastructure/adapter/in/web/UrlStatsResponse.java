package com.example.urlshortener.infrastructure.adapter.in.web;

import com.example.urlshortener.domain.model.UrlStats;

import java.time.LocalDate;
import java.util.List;

public record UrlStatsResponse(
        String shortCode, String originalUrl, long totalClicks, List<DailyClicksResponse> clicksPerDay) {

    public record DailyClicksResponse(LocalDate date, long clicks) {
    }

    public static UrlStatsResponse fromDomain(UrlStats urlStats) {
        List<DailyClicksResponse> clicksPerDay = urlStats.clicksPerDay()
                .mapEach(daily -> new DailyClicksResponse(daily.day(), daily.clicks()));

        return new UrlStatsResponse(
                urlStats.shortCode().value(),
                urlStats.originalUrl().url(),
                urlStats.totalClicks(),
                clicksPerDay);
    }
}
