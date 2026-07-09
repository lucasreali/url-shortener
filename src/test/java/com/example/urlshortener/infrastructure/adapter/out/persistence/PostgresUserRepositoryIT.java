package com.example.urlshortener.infrastructure.adapter.out.persistence;

import com.example.urlshortener.IntegrationTest;
import com.example.urlshortener.domain.model.Email;
import com.example.urlshortener.domain.model.PasswordHash;
import com.example.urlshortener.domain.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PostgresUserRepositoryIT extends IntegrationTest {

    @Autowired
    private PostgresUserRepository repository;

    @Test
    void persistsAndReadsBackUser() {
        User user = User.register(new Email("repoit@example.com"), new PasswordHash("$2a$10$hash"));

        User saved = repository.add(user);
        Optional<User> found = repository.findByEmail(new Email("repoit@example.com"));

        assertEquals(user, saved);
        assertEquals(Optional.of(user), found);
    }

    @Test
    void findsNothingForUnknownEmail() {
        assertEquals(Optional.empty(), repository.findByEmail(new Email("missing@example.com")));
    }
}
