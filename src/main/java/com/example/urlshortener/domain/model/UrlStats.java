package com.example.urlshortener.domain.model;

public record UrlStats(ShortCode shortCode, OriginalUrl originalUrl, long totalClicks) {
}
