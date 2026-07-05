package com.example.urlshortener.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShortCodeTest {

    @ParameterizedTest
    @ValueSource(strings = {"abc123", "ABCdef", "000000", "abcdefghij"})
    void acceptsAlphanumericCodesBetweenSixAndTenCharacters(String value) {
        assertEquals(value, new ShortCode(value).value());
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc12", "abcdefghijk", "abc 12", "abc-12", "abç123", ""})
    void rejectsCodesOutsideSixToTenAlphanumericCharacters(String value) {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new ShortCode(value));
        assertEquals("Invalid short code: " + value, exception.getMessage());
    }

    @Test
    void rejectsNullCode() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new ShortCode(null));
        assertEquals("Short code can't be null", exception.getMessage());
    }
}
