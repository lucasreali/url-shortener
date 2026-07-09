package com.example.urlshortener.infrastructure.adapter.out.persistence;

import com.example.urlshortener.domain.model.Email;
import com.example.urlshortener.domain.model.PasswordHash;
import com.example.urlshortener.domain.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "users")
public class UserJpaEntity {

    @Id
    private UUID id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    protected UserJpaEntity() {
    }

    public static UserJpaEntity fromDomain(User user) {
        UserJpaEntity entity = new UserJpaEntity();
        entity.id = user.id();
        entity.email = user.email().value();
        entity.passwordHash = user.passwordHash().value();

        return entity;
    }

    public User toDomain() {
        return new User(id, new Email(email), new PasswordHash(passwordHash));
    }
}
