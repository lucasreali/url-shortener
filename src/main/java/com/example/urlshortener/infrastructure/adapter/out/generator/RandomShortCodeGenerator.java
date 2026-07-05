package com.example.urlshortener.infrastructure.adapter.out.generator;

import com.example.urlshortener.application.port.out.ShortCodeGenerator;
import com.example.urlshortener.domain.model.ShortCode;

import java.security.SecureRandom;

public class RandomShortCodeGenerator implements ShortCodeGenerator {

    @Override
    public ShortCode generate() {
        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom random = new SecureRandom();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characters.length());
            char character = characters.charAt(index);
            builder.append(character);
        }

        return new ShortCode(builder.toString());
    }
}
