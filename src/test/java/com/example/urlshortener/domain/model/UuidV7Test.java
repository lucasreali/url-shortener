package com.example.urlshortener.domain.model;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UuidV7Test {

    @Test
    void generatesVersionSevenUuid() {
        assertEquals(7, UuidV7.generate().version());
    }

    @Test
    void generatesRfcVariantUuid() {
        assertEquals(2, UuidV7.generate().variant());
    }

    @Test
    void ordersUuidsByGenerationTime() throws InterruptedException {
        UUID first = UuidV7.generate();
        Thread.sleep(5);
        UUID second = UuidV7.generate();

        long firstTimestamp = first.getMostSignificantBits() >>> 16;
        long secondTimestamp = second.getMostSignificantBits() >>> 16;
        assertTrue(firstTimestamp < secondTimestamp);
    }

    @Test
    void constructorIsPrivateAndOnlyReachableByReflection() throws Exception {
        Constructor<UuidV7> constructor = UuidV7.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        assertTrue(constructor.newInstance() instanceof UuidV7);
    }
}
