package com.example.urlshortener.infrastructure.adapter.in.web;

import com.example.urlshortener.application.exception.ShortUrlNotFoundException;
import com.example.urlshortener.application.port.in.GetUrlStatsUseCase;
import com.example.urlshortener.domain.model.DailyClickCounts;
import com.example.urlshortener.domain.model.DailyClicks;
import com.example.urlshortener.domain.model.OriginalUrl;
import com.example.urlshortener.domain.model.ShortCode;
import com.example.urlshortener.domain.model.UrlStats;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatsController.class)
class StatsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GetUrlStatsUseCase getUrlStatsUseCase;

    @Test
    void answersStatsWithTotalAndPerDayClicks() throws Exception {
        when(getUrlStatsUseCase.get("abc123")).thenReturn(new UrlStats(
                new ShortCode("abc123"),
                new OriginalUrl("https://example.com"),
                new DailyClickCounts(List.of(
                        new DailyClicks(LocalDate.parse("2026-07-04"), 40),
                        new DailyClicks(LocalDate.parse("2026-07-05"), 2)))));

        mockMvc.perform(get("/abc123/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortCode").value("abc123"))
                .andExpect(jsonPath("$.originalUrl").value("https://example.com"))
                .andExpect(jsonPath("$.totalClicks").value(42))
                .andExpect(jsonPath("$.clicksPerDay[0].date").value("2026-07-04"))
                .andExpect(jsonPath("$.clicksPerDay[0].clicks").value(40))
                .andExpect(jsonPath("$.clicksPerDay[1].date").value("2026-07-05"))
                .andExpect(jsonPath("$.clicksPerDay[1].clicks").value(2));
    }

    @Test
    void answersNotFoundForUnknownShortCode() throws Exception {
        when(getUrlStatsUseCase.get("abc123"))
                .thenThrow(new ShortUrlNotFoundException(new ShortCode("abc123")));

        mockMvc.perform(get("/abc123/stats"))
                .andExpect(status().isNotFound());
    }

    @Test
    void answersBadRequestForMalformedShortCode() throws Exception {
        when(getUrlStatsUseCase.get("ab"))
                .thenThrow(new IllegalArgumentException("Invalid short code: ab"));

        mockMvc.perform(get("/ab/stats"))
                .andExpect(status().isBadRequest());
    }
}
