package com.example.urlshortener.infrastructure.adapter.out.security;

import com.example.urlshortener.application.port.out.TokenIssuer;
import com.example.urlshortener.domain.model.AccessToken;
import com.example.urlshortener.domain.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
public class JwtTokenIssuer implements TokenIssuer {
    private final JwtEncoder jwtEncoder;
    private final Duration expiresIn;

    public JwtTokenIssuer(JwtEncoder jwtEncoder, @Value("${app.jwt.expires-in}") Duration expiresIn) {
        this.jwtEncoder = jwtEncoder;
        this.expiresIn = expiresIn;
    }

    @Override
    public AccessToken issueFor(User user) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(user.id().toString())
                .claim("email", user.email().value())
                .issuedAt(now)
                .expiresAt(now.plus(expiresIn))
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        return new AccessToken(jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue());
    }
}
