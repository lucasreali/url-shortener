package com.example.urlshortener.application.service;

import com.example.urlshortener.application.exception.EmailAlreadyInUseException;
import com.example.urlshortener.application.port.out.PasswordHasher;
import com.example.urlshortener.application.port.out.UserRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterUserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordHasher passwordHasher;

    @InjectMocks
    private RegisterUserService service;

    @Test
    void persistsUserWithHashedPassword() {
        Email email = new Email("lucas@example.com");
        PasswordHash hash = new PasswordHash("$2a$10$hash");
        when(repository.findByEmail(email)).thenReturn(Optional.empty());
        when(passwordHasher.hash("123456")).thenReturn(hash);
        when(repository.add(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User user = service.register("lucas@example.com", "123456");

        assertEquals(email, user.email());
        assertEquals(hash, user.passwordHash());
        verify(repository).add(user);
    }

    @Test
    void rejectsEmailAlreadyInUse() {
        Email email = new Email("lucas@example.com");
        User existing = User.register(email, new PasswordHash("$2a$10$hash"));
        when(repository.findByEmail(email)).thenReturn(Optional.of(existing));

        EmailAlreadyInUseException exception = assertThrows(EmailAlreadyInUseException.class,
                () -> service.register("lucas@example.com", "123456"));

        assertEquals("Email already in use: lucas@example.com", exception.getMessage());
        verify(repository, never()).add(any(User.class));
        verifyNoInteractions(passwordHasher);
    }

    @Test
    void rejectsInvalidEmailBeforeTouchingAnyPort() {
        assertThrows(IllegalArgumentException.class, () -> service.register("not-an-email", "123456"));

        verifyNoInteractions(repository, passwordHasher);
    }
}
