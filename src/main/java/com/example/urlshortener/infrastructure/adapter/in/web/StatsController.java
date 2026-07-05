package com.example.urlshortener.infrastructure.adapter.in.web;


import com.example.urlshortener.application.port.in.GetUrlStatsUseCase;
import com.example.urlshortener.domain.model.UrlStats;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatsController {

    private final GetUrlStatsUseCase getUrlStats;

    public StatsController(GetUrlStatsUseCase getUrlStats) {
        this.getUrlStats = getUrlStats;
    }

    @GetMapping("/{shortCode}/stats")
    public ResponseEntity<UrlStatsResponse> stats(@PathVariable String shortCode) {
        UrlStats urlStats = getUrlStats.get(shortCode);

        return ResponseEntity.ok(
                new UrlStatsResponse(
                    urlStats.shortCode().value(),
                    urlStats.originalUrl().url(),
                    urlStats.totalClicks())
        );
    }
}
