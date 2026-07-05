package com.example.urlshortener.infrastructure.adapter.out.messaging;

import com.example.urlshortener.application.port.out.UrlAccessPublisher;
import com.example.urlshortener.domain.model.ShortCode;
import com.example.urlshortener.infrastructure.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitUrlAccessPublisher implements UrlAccessPublisher {

    private final RabbitTemplate rabbitTemplate;

    public RabbitUrlAccessPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @Override
    public void publish(ShortCode shortCode) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.URL_EVENTS_EXCHANGE, RabbitMQConfig.URL_ACCESSED_ROUTING_KEY, shortCode.value());
    }
}
