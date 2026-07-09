package com.example.urlshortener.infrastructure.adapter.in.web;

import com.example.urlshortener.application.exception.ShortUrlNotFoundException;
import com.example.urlshortener.application.port.in.ResolveUrlUseCase;
import com.example.urlshortener.domain.model.OriginalUrl;
import com.example.urlshortener.domain.model.ShortCode;
import com.example.urlshortener.infrastructure.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RedirectController.class)
@Import(SecurityConfig.class)
class RedirectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ResolveUrlUseCase resolveUrlUseCase;

    @Test
    void redirectsToOriginalUrl() throws Exception {
        when(resolveUrlUseCase.resolve("abc123")).thenReturn(new OriginalUrl("https://example.com"));

        mockMvc.perform(get("/abc123"))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "https://example.com"));
    }

    @Test
    void answersNotFoundForUnknownShortCode() throws Exception {
        when(resolveUrlUseCase.resolve("abc123"))
                .thenThrow(new ShortUrlNotFoundException(new ShortCode("abc123")));

        mockMvc.perform(get("/abc123"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value(
                        "Short URL not found for code: ShortCode[value=abc123]"));
    }

    @Test
    void answersBadRequestForMalformedShortCode() throws Exception {
        when(resolveUrlUseCase.resolve("ab"))
                .thenThrow(new IllegalArgumentException("Invalid short code: ab"));

        mockMvc.perform(get("/ab"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Invalid short code: ab"));
    }
}
