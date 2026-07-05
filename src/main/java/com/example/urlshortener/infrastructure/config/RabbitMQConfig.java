package com.example.urlshortener.infrastructure.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String URL_ACCESSED_QUEUE = "url.accessed";

    @Bean
    public Queue urlAccessedQueue() {
        return new Queue(URL_ACCESSED_QUEUE, true);
    }
}
