package com.example.urlshortener.infrastructure.adapter.in.messaging;

import com.example.urlshortener.application.port.in.RegisterUrlAccessUseCase;
import com.example.urlshortener.infrastructure.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UrlAccessConsumer {

    private final RegisterUrlAccessUseCase registerUrlAccess;


    public UrlAccessConsumer(RegisterUrlAccessUseCase registerUrlAccess) {
        this.registerUrlAccess = registerUrlAccess;
    }

    @RabbitListener(queues = RabbitMQConfig.URL_ACCESSED_QUEUE)
    public void onUrlAccessed(String shortCode) {
        registerUrlAccess.register(shortCode);
    }
}
