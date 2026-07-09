package com.example.urlshortener.application.service;

import com.example.urlshortener.application.exception.InvalidCredentialsException;
import com.example.urlshortener.application.port.out.PasswordHasher;
import com.example.urlshortener.application.port.out.TokenIssuer;
import com.example.urlshortener.application.port.out.UserRepository;
import com.example.urlshortener.domain.model.AccessToken;
import com.example.urlshortener.domain.model.Email;
import com.example.urlshortener.domain.model.PasswordHash;
import com.example.urlshortener.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    private static final Email EMAIL = new Email("lucas@example.com");
    private static final PasswordHash PASSWORD_HASH = new PasswordHash("$2a$10$hash");

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordHasher passwordHasher;

    @Mock
    private TokenIssuer tokenIssuer;

    @InjectMocks
    private LoginService service;

    @Test
    void issuesTokenForValidCredentials() {
        User user = User.register(EMAIL, PASSWORD_HASH);
        when(repository.findByEmail(EMAIL)).thenReturn(Optional.of(user));
        when(passwordHasher.matches("123456", PASSWORD_HASH)).thenReturn(true);
        when(tokenIssuer.issueFor(user)).thenReturn(new AccessToken("token123"));

        AccessToken token = service.login("lucas@example.com", "123456");

        assertEquals("token123", token.value());
    }

    @Test
    void rejectsUnknownEmail() {
        when(repository.findByEmail(EMAIL)).thenReturn(Optional.empty());

        assertThrows(InvalidCredentialsException.class,
                () -> service.login("lucas@example.com", "123456"));

        verifyNoInteractions(passwordHasher, tokenIssuer);
    }

    @Test
    void rejectsWrongPassword() {
        User user = User.register(EMAIL, PASSWORD_HASH);
        when(repository.findByEmail(EMAIL)).thenReturn(Optional.of(user));
        when(passwordHasher.matches("wrong", PASSWORD_HASH)).thenReturn(false);

        assertThrows(InvalidCredentialsException.class,
                () -> service.login("lucas@example.com", "wrong"));

        verifyNoInteractions(tokenIssuer);
    }
}
