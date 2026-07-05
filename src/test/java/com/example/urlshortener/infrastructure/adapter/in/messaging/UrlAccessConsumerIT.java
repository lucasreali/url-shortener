package com.example.urlshortener.infrastructure.adapter.in.messaging;

import com.example.urlshortener.IntegrationTest;
import com.example.urlshortener.application.port.out.UrlAccessPublisher;
import com.example.urlshortener.domain.model.ShortCode;
import com.example.urlshortener.infrastructure.adapter.out.persistence.UrlAccessJpaEntity;
import com.example.urlshortener.infrastructure.adapter.out.persistence.UrlAccessJpaRepository;
import com.example.urlshortener.infrastructure.config.RabbitMQConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UrlAccessConsumerIT extends IntegrationTest {

    @Autowired
    private UrlAccessPublisher publisher;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UrlAccessJpaRepository urlAccessJpaRepository;

    @Test
    void persistsUrlAccessWhenEventArrivesThroughNamedExchange() {
        ShortCode shortCode = new ShortCode("evtflow1");

        publisher.publish(shortCode);

        await().atMost(Duration.ofSeconds(15)).until(() -> accessWasPersisted(shortCode));
    }

    @Test
    void deadLettersMessageAfterRepeatedConsumerFailures() {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.URL_EVENTS_EXCHANGE, RabbitMQConfig.URL_ACCESSED_ROUTING_KEY, "!!");

        Message deadLetter = await().atMost(Duration.ofSeconds(15))
                .until(() -> rabbitTemplate.receive(RabbitMQConfig.URL_ACCESSED_DEAD_LETTER_QUEUE), message -> message != null);

        assertEquals("!!", new String(deadLetter.getBody(), StandardCharsets.UTF_8));
    }

    private boolean accessWasPersisted(ShortCode shortCode) {
        return urlAccessJpaRepository.findAll().stream()
                .map(UrlAccessJpaEntity::toDomain)
                .anyMatch(access -> access.shortCode().equals(shortCode));
    }
}
