package com.example.urlshortener.infrastructure.adapter.in.web;

import com.example.urlshortener.application.port.in.LoginUseCase;
import com.example.urlshortener.application.port.in.RegisterUserUseCase;
import com.example.urlshortener.domain.model.AccessToken;
import com.example.urlshortener.domain.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUseCase loginUseCase;

    public AuthController(RegisterUserUseCase registerUserUseCase, LoginUseCase loginUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        User user = registerUserUseCase.register(request.email(), request.password());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new RegisterResponse(user.id(), user.email().value()));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        AccessToken token = loginUseCase.login(request.email(), request.password());

        return ResponseEntity.ok(new LoginResponse(token.value()));
    }
}
