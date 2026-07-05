package com.example.urlshortener.infrastructure.adapter.in.web;

public record UrlStatsResponse(String shortCode, String originalUrl, long totalClicks) {
}
