package com.example.urlshortener.application.service;

import com.example.urlshortener.application.exception.ShortUrlNotFoundException;
import com.example.urlshortener.application.port.out.ShortUrlRepository;
import com.example.urlshortener.application.port.out.UrlAccessRepository;
import com.example.urlshortener.domain.model.DailyClickCounts;
import com.example.urlshortener.domain.model.DailyClicks;
import com.example.urlshortener.domain.model.OriginalUrl;
import com.example.urlshortener.domain.model.ShortCode;
import com.example.urlshortener.domain.model.ShortUrl;
import com.example.urlshortener.domain.model.UrlStats;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUrlStatsServiceTest {

    private static final ShortCode SHORT_CODE = new ShortCode("abc123");
    private static final OriginalUrl ORIGINAL_URL = new OriginalUrl("https://example.com");

    @Mock
    private UrlAccessRepository urlAccessRepository;

    @Mock
    private ShortUrlRepository shortUrlRepository;

    @InjectMocks
    private GetUrlStatsService service;

    @Test
    void assemblesStatsFromUrlAndDailyCounts() {
        DailyClickCounts clicksPerDay = new DailyClickCounts(List.of(
                new DailyClicks(LocalDate.parse("2026-07-04"), 40),
                new DailyClicks(LocalDate.parse("2026-07-05"), 2)));
        when(shortUrlRepository.find(SHORT_CODE))
                .thenReturn(Optional.of(ShortUrl.record(SHORT_CODE, ORIGINAL_URL)));
        when(urlAccessRepository.countPerDay(SHORT_CODE)).thenReturn(clicksPerDay);

        UrlStats urlStats = service.get("abc123");

        assertEquals(new UrlStats(SHORT_CODE, ORIGINAL_URL, clicksPerDay), urlStats);
        assertEquals(42, urlStats.totalClicks());
    }

    @Test
    void failsWithoutCountingWhenShortCodeIsUnknown() {
        when(shortUrlRepository.find(SHORT_CODE)).thenReturn(Optional.empty());

        assertThrows(ShortUrlNotFoundException.class, () -> service.get("abc123"));

        verifyNoInteractions(urlAccessRepository);
    }
}
