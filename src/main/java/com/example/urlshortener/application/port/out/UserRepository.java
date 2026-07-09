package com.example.urlshortener.application.port.out;

import com.example.urlshortener.domain.model.Email;
import com.example.urlshortener.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    User add(User user);
    Optional<User> findByEmail(Email email);
}
