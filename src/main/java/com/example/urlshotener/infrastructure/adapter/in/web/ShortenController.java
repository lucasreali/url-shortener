package com.example.urlshotener.infrastructure.adapter.in.web;

import com.example.urlshotener.application.port.in.ShortenUrlUseCase;
import com.example.urlshotener.domain.model.ShortUrl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/urls")
public class ShortenController {

    private final ShortenUrlUseCase shortenUrlUseCase;

    public ShortenController(ShortenUrlUseCase shortenUrlUseCase) {
        this.shortenUrlUseCase = shortenUrlUseCase;
    }

    @PostMapping("/shorten")
    public ShortenUrlResponse shorten(@RequestBody ShortenUrlRequest request) {
        ShortUrl shortUrl = shortenUrlUseCase.shorten(request.url());
        return new ShortenUrlResponse(shortUrl.shortCode().value());
    }
}
