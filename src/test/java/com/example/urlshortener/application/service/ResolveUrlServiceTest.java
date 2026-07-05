package com.example.urlshortener.application.service;

import com.example.urlshortener.application.exception.ShortUrlNotFoundException;
import com.example.urlshortener.application.port.out.ShortUrlRepository;
import com.example.urlshortener.application.port.out.UrlAccessPublisher;
import com.example.urlshortener.application.port.out.UrlCache;
import com.example.urlshortener.domain.model.OriginalUrl;
import com.example.urlshortener.domain.model.ShortCode;
import com.example.urlshortener.domain.model.ShortUrl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResolveUrlServiceTest {

    private static final ShortCode SHORT_CODE = new ShortCode("abc123");
    private static final OriginalUrl ORIGINAL_URL = new OriginalUrl("https://example.com");

    @Mock
    private ShortUrlRepository repository;

    @Mock
    private UrlCache cache;

    @Mock
    private UrlAccessPublisher publisher;

    @InjectMocks
    private ResolveUrlService service;

    @Test
    void resolvesFromCacheWithoutTouchingRepository() {
        when(cache.find(SHORT_CODE)).thenReturn(Optional.of(ORIGINAL_URL));

        OriginalUrl resolved = service.resolve("abc123");

        assertEquals(ORIGINAL_URL, resolved);
        verify(publisher).publish(SHORT_CODE);
        verifyNoInteractions(repository);
    }

    @Test
    void resolvesFromRepositoryAndCachesOnCacheMiss() {
        when(cache.find(SHORT_CODE)).thenReturn(Optional.empty());
        when(repository.find(SHORT_CODE)).thenReturn(Optional.of(ShortUrl.record(SHORT_CODE, ORIGINAL_URL)));

        OriginalUrl resolved = service.resolve("abc123");

        assertEquals(ORIGINAL_URL, resolved);
        verify(cache).put(SHORT_CODE, ORIGINAL_URL);
        verify(publisher).publish(SHORT_CODE);
    }

    @Test
    void failsWithoutPublishingWhenShortCodeIsUnknown() {
        when(cache.find(SHORT_CODE)).thenReturn(Optional.empty());
        when(repository.find(SHORT_CODE)).thenReturn(Optional.empty());

        assertThrows(ShortUrlNotFoundException.class, () -> service.resolve("abc123"));

        verify(publisher, never()).publish(SHORT_CODE);
    }
}
