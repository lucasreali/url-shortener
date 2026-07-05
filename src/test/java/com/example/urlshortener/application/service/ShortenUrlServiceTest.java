package com.example.urlshortener.application.service;

import com.example.urlshortener.application.port.out.ShortCodeGenerator;
import com.example.urlshortener.application.port.out.ShortUrlRepository;
import com.example.urlshortener.domain.model.ShortCode;
import com.example.urlshortener.domain.model.ShortUrl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShortenUrlServiceTest {

    @Mock
    private ShortUrlRepository repository;

    @Mock
    private ShortCodeGenerator generator;

    @InjectMocks
    private ShortenUrlService service;

    @Test
    void persistsShortUrlWithGeneratedCode() {
        ShortCode shortCode = new ShortCode("abc123");
        when(generator.generate()).thenReturn(shortCode);
        when(repository.add(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ShortUrl shortUrl = service.shorten("https://example.com");

        assertEquals(shortCode, shortUrl.shortCode());
        assertEquals("https://example.com", shortUrl.originalUrl().url());
        verify(repository).add(shortUrl);
    }

    @Test
    void rejectsInvalidUrlBeforeTouchingAnyPort() {
        assertThrows(IllegalArgumentException.class, () -> service.shorten("http://exa mple.com"));

        verifyNoInteractions(generator, repository);
    }
}
