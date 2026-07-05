package com.example.urlshortener.application.service;

import com.example.urlshortener.application.exception.ShortUrlNotFoundException;
import com.example.urlshortener.application.port.in.GetUrlStatsUseCase;
import com.example.urlshortener.application.port.out.ShortUrlRepository;
import com.example.urlshortener.application.port.out.UrlAccessRepository;
import com.example.urlshortener.domain.model.DailyClickCounts;
import com.example.urlshortener.domain.model.ShortCode;
import com.example.urlshortener.domain.model.ShortUrl;
import com.example.urlshortener.domain.model.UrlStats;
import org.springframework.stereotype.Service;

@Service
public class GetUrlStatsService implements GetUrlStatsUseCase {

    private final UrlAccessRepository urlAccessRepository;
    private final ShortUrlRepository shortUrlRepository;

    public GetUrlStatsService(UrlAccessRepository urlAccessRepository, ShortUrlRepository shortUrlRepository) {
        this.urlAccessRepository = urlAccessRepository;
        this.shortUrlRepository = shortUrlRepository;
    }

    @Override
    public UrlStats get(String shortCode) {
        ShortCode code = new ShortCode(shortCode);
        ShortUrl shortUrl = shortUrlRepository.find(code)
                .orElseThrow(() -> new ShortUrlNotFoundException(code));
        DailyClickCounts clicksPerDay = urlAccessRepository.countPerDay(code);
        return new UrlStats(code, shortUrl.originalUrl(), clicksPerDay);
    }
}
