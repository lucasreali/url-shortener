package com.example.urlshortener.infrastructure.adapter.out.persistence;

import com.example.urlshortener.application.port.out.UserRepository;
import com.example.urlshortener.domain.model.Email;
import com.example.urlshortener.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PostgresUserRepository implements UserRepository {
    private final UserJpaRepository jpaRepository;

    public PostgresUserRepository(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public User add(User user) {
        UserJpaEntity entity = UserJpaEntity.fromDomain(user);
        return jpaRepository.save(entity).toDomain();
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return jpaRepository.findByEmail(email.value())
                .map(UserJpaEntity::toDomain);
    }
}
