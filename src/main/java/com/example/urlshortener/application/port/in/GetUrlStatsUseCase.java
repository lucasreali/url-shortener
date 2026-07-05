package com.example.urlshortener.application.port.in;

import com.example.urlshortener.domain.model.UrlStats;

public interface GetUrlStatsUseCase {
    UrlStats get(String shortCode);
}
