package com.example.urlshotener.infrastructure.adapter.in.web;

import com.example.urlshotener.application.port.in.ResolveUrlUseCase;
import com.example.urlshotener.domain.model.OriginalUrl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class RedirectController {

    private final ResolveUrlUseCase resolveUrlUseCase;

    public RedirectController(ResolveUrlUseCase resolveUrlUseCase) {
        this.resolveUrlUseCase = resolveUrlUseCase;
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortCode) {
        OriginalUrl originalUrl = resolveUrlUseCase.resolve(shortCode);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(originalUrl.url())).build();
    }
}
