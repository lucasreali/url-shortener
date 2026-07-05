package com.example.urlshortener.infrastructure.adapter.in.web;

import com.example.urlshortener.application.port.in.ShortenUrlUseCase;
import com.example.urlshortener.domain.model.ShortUrl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController()
public class ShortenController {

    private final ShortenUrlUseCase shortenUrlUseCase;
    private final String baseUrl;

    public ShortenController(ShortenUrlUseCase shortenUrlUseCase, @Value("${app.base-url}") String baseUrl) {
        this.shortenUrlUseCase = shortenUrlUseCase;
        this.baseUrl = baseUrl;
    }

    @PostMapping("/shorten")
    public ResponseEntity<ShortenUrlResponse> shorten(@RequestBody ShortenUrlRequest request) {
        ShortUrl shortUrl = shortenUrlUseCase.shorten(request.url());
        String fullShortUrl = baseUrl + "/" + shortUrl.shortCode().value();
        ShortenUrlResponse response = new ShortenUrlResponse(shortUrl.shortCode().value(), fullShortUrl);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create(fullShortUrl))
                .body(response);
    }
}
