package com.example.urlshortener.infrastructure.adapter.in.web;

import com.example.urlshortener.application.exception.EmailAlreadyInUseException;
import com.example.urlshortener.application.exception.InvalidCredentialsException;
import com.example.urlshortener.application.port.in.LoginUseCase;
import com.example.urlshortener.application.port.in.RegisterUserUseCase;
import com.example.urlshortener.domain.model.AccessToken;
import com.example.urlshortener.domain.model.Email;
import com.example.urlshortener.domain.model.PasswordHash;
import com.example.urlshortener.domain.model.User;
import com.example.urlshortener.infrastructure.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RegisterUserUseCase registerUserUseCase;

    @MockitoBean
    private LoginUseCase loginUseCase;

    @Test
    void registersUser() throws Exception {
        User user = User.register(new Email("lucas@example.com"), new PasswordHash("$2a$10$hash"));
        when(registerUserUseCase.register("lucas@example.com", "123456")).thenReturn(user);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"lucas@example.com\", \"password\": \"123456\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(user.id().toString()))
                .andExpect(jsonPath("$.email").value("lucas@example.com"));
    }

    @Test
    void answersConflictForEmailAlreadyInUse() throws Exception {
        when(registerUserUseCase.register("lucas@example.com", "123456"))
                .thenThrow(new EmailAlreadyInUseException(new Email("lucas@example.com")));

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"lucas@example.com\", \"password\": \"123456\"}"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.detail").value("Email already in use: lucas@example.com"));
    }

    @Test
    void answersTokenForValidLogin() throws Exception {
        when(loginUseCase.login("lucas@example.com", "123456")).thenReturn(new AccessToken("token123"));

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"lucas@example.com\", \"password\": \"123456\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("token123"));
    }

    @Test
    void answersUnauthorizedForInvalidCredentials() throws Exception {
        when(loginUseCase.login("lucas@example.com", "wrong"))
                .thenThrow(new InvalidCredentialsException());

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"lucas@example.com\", \"password\": \"wrong\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.detail").value("Invalid credentials"));
    }
}
