package com.example.urlshortener.application.service;

import com.example.urlshortener.application.port.in.RegisterUrlAccessUseCase;
import com.example.urlshortener.application.port.out.UrlAccessRepository;
import com.example.urlshortener.domain.model.ShortCode;
import com.example.urlshortener.domain.model.UrlAccess;
import org.springframework.stereotype.Service;

@Service
public class RegisterUrlAccessService implements RegisterUrlAccessUseCase {

    private final UrlAccessRepository repository;

    public RegisterUrlAccessService(UrlAccessRepository repository) {
        this.repository = repository;
    }

    @Override
    public void register(String shortCode) {
        UrlAccess urlAccess = UrlAccess.record(new ShortCode(shortCode));
        repository.add(urlAccess);
    }
}
