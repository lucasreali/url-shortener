package com.example.urlshortener.application.service;

import com.example.urlshortener.application.port.out.UrlAccessRepository;
import com.example.urlshortener.domain.model.ShortCode;
import com.example.urlshortener.domain.model.UrlAccess;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class RegisterUrlAccessServiceTest {

    @Mock
    private UrlAccessRepository repository;

    @InjectMocks
    private RegisterUrlAccessService service;

    @Test
    void persistsAccessForValidShortCode() {
        service.register("abc123");

        ArgumentCaptor<UrlAccess> captor = ArgumentCaptor.forClass(UrlAccess.class);
        verify(repository).add(captor.capture());
        assertEquals(new ShortCode("abc123"), captor.getValue().shortCode());
    }

    @Test
    void rejectsInvalidShortCodeWithoutPersisting() {
        assertThrows(IllegalArgumentException.class, () -> service.register("!!"));

        verifyNoInteractions(repository);
    }
}
