package com.example.urlshortener.infrastructure.adapter.out.security;

import com.example.urlshortener.domain.model.AccessToken;
import com.example.urlshortener.domain.model.Email;
import com.example.urlshortener.domain.model.PasswordHash;
import com.example.urlshortener.domain.model.User;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JwtTokenIssuerTest {

    private static final String SECRET = "0123456789abcdef0123456789abcdef";
    private static final Duration EXPIRES_IN = Duration.ofHours(1);

    private final JwtTokenIssuer issuer = new JwtTokenIssuer(
            new NimbusJwtEncoder(new ImmutableSecret<>(SECRET.getBytes(StandardCharsets.UTF_8))),
            EXPIRES_IN);

    @Test
    void issuesSignedTokenWithUserClaims() {
        User user = User.register(new Email("lucas@example.com"), new PasswordHash("$2a$10$hash"));

        AccessToken token = issuer.issueFor(user);
        Jwt jwt = decode(token);

        assertEquals(user.id().toString(), jwt.getSubject());
        assertEquals("lucas@example.com", jwt.getClaim("email"));
        assertEquals(jwt.getIssuedAt().plus(EXPIRES_IN), jwt.getExpiresAt());
    }

    private Jwt decode(AccessToken token) {
        NimbusJwtDecoder decoder = NimbusJwtDecoder
                .withSecretKey(new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256"))
                .macAlgorithm(MacAlgorithm.HS256)
                .build();

        return decoder.decode(token.value());
    }
}
