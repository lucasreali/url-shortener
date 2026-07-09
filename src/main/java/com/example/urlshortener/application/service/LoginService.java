package com.example.urlshortener.application.service;

import com.example.urlshortener.application.exception.InvalidCredentialsException;
import com.example.urlshortener.application.port.in.LoginUseCase;
import com.example.urlshortener.application.port.out.PasswordHasher;
import com.example.urlshortener.application.port.out.TokenIssuer;
import com.example.urlshortener.application.port.out.UserRepository;
import com.example.urlshortener.domain.model.AccessToken;
import com.example.urlshortener.domain.model.Email;
import com.example.urlshortener.domain.model.User;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements LoginUseCase {
    private final UserRepository repository;
    private final PasswordHasher passwordHasher;
    private final TokenIssuer tokenIssuer;

    public LoginService(UserRepository repository, PasswordHasher passwordHasher, TokenIssuer tokenIssuer) {
        this.repository = repository;
        this.passwordHasher = passwordHasher;
        this.tokenIssuer = tokenIssuer;
    }

    @Override
    public AccessToken login(String email, String rawPassword) {
        User user = this.repository.findByEmail(new Email(email))
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordHasher.matches(rawPassword, user.passwordHash())) {
            throw new InvalidCredentialsException();
        }

        return tokenIssuer.issueFor(user);
    }
}
