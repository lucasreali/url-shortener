package com.example.urlshortener.infrastructure.adapter.out.generator;

import com.example.urlshortener.IntegrationTest;
import com.example.urlshortener.domain.model.ShortCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RedisShortCodeGeneratorIT extends IntegrationTest {

    @Autowired
    private RedisShortCodeGenerator generator;

    @Test
    void generatesDistinctCodesForConsecutiveCalls() {
        ShortCode first = generator.generate();
        ShortCode second = generator.generate();

        assertNotEquals(first, second);
    }

    @Test
    void generatesCodesWithinDomainLengthLimits() {
        // Sqids com minLength(6) e alfabeto de 62 chars: passar de 10 chars exigiria
        // um contador na casa de 62^9 (~10^16) — inalcançável na prática.
        ShortCode shortCode = generator.generate();

        int length = shortCode.value().length();
        assertTrue(length >= 6 && length <= 10);
    }
}
