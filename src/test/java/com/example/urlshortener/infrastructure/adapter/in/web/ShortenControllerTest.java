package com.example.urlshortener.infrastructure.adapter.in.web;

import com.example.urlshortener.application.port.in.ShortenUrlUseCase;
import com.example.urlshortener.domain.model.OriginalUrl;
import com.example.urlshortener.domain.model.ShortCode;
import com.example.urlshortener.domain.model.ShortUrl;
import com.example.urlshortener.infrastructure.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShortenController.class)
@Import(SecurityConfig.class)
class ShortenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ShortenUrlUseCase shortenUrlUseCase;

    @Test
    void createsShortUrlWithLocationHeader() throws Exception {
        when(shortenUrlUseCase.shorten("https://example.com"))
                .thenReturn(ShortUrl.record(new ShortCode("abc123"), new OriginalUrl("https://example.com")));

        mockMvc.perform(post("/shorten")
                        .with(jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"url\": \"https://example.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost:8080/abc123"))
                .andExpect(jsonPath("$.shortCode").value("abc123"))
                .andExpect(jsonPath("$.shortUrl").value("http://localhost:8080/abc123"));
    }

    @Test
    void answersBadRequestForInvalidUrl() throws Exception {
        when(shortenUrlUseCase.shorten("nope"))
                .thenThrow(new IllegalArgumentException("Invalid URL: nope"));

        mockMvc.perform(post("/shorten")
                        .with(jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"url\": \"nope\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Invalid URL: nope"));
    }

    @Test
    void answersUnauthorizedWithoutToken() throws Exception {
        mockMvc.perform(post("/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"url\": \"https://example.com\"}"))
                .andExpect(status().isUnauthorized());
    }
}
