package com.example.urlshortener.application.service;

import com.example.urlshortener.application.exception.EmailAlreadyInUseException;
import com.example.urlshortener.application.port.in.RegisterUserUseCase;
import com.example.urlshortener.application.port.out.PasswordHasher;
import com.example.urlshortener.application.port.out.UserRepository;
import com.example.urlshortener.domain.model.Email;
import com.example.urlshortener.domain.model.User;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserService implements RegisterUserUseCase {
    private final UserRepository repository;
    private final PasswordHasher passwordHasher;

    public RegisterUserService(UserRepository repository, PasswordHasher passwordHasher) {
        this.repository = repository;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public User register(String email, String rawPassword) {
        Email userEmail = new Email(email);

        if (this.repository.findByEmail(userEmail).isPresent()) {
            throw new EmailAlreadyInUseException(userEmail);
        }

        User user = User.register(userEmail, passwordHasher.hash(rawPassword));

        return this.repository.add(user);
    }
}
