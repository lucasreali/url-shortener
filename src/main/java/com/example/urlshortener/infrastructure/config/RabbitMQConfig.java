package com.example.urlshortener.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String URL_EVENTS_EXCHANGE = "url.events";
    public static final String URL_EVENTS_DEAD_LETTER_EXCHANGE = "url.events.dlx";
    public static final String URL_ACCESSED_ROUTING_KEY = "url.accessed";
    public static final String URL_ACCESSED_QUEUE = "url.accessed";
    public static final String URL_ACCESSED_DEAD_LETTER_QUEUE = "url.accessed.dlq";

    @Bean
    public DirectExchange urlEventsExchange() {
        return new DirectExchange(URL_EVENTS_EXCHANGE, true, false);
    }

    @Bean
    public Queue urlAccessedQueue() {
        return QueueBuilder.durable(URL_ACCESSED_QUEUE)
                .deadLetterExchange(URL_EVENTS_DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey(URL_ACCESSED_ROUTING_KEY)
                .build();
    }

    @Bean
    public Binding urlAccessedBinding(Queue urlAccessedQueue, DirectExchange urlEventsExchange) {
        return BindingBuilder.bind(urlAccessedQueue).to(urlEventsExchange).with(URL_ACCESSED_ROUTING_KEY);
    }

    @Bean
    public DirectExchange urlEventsDeadLetterExchange() {
        return new DirectExchange(URL_EVENTS_DEAD_LETTER_EXCHANGE, true, false);
    }

    @Bean
    public Queue urlAccessedDeadLetterQueue() {
        return QueueBuilder.durable(URL_ACCESSED_DEAD_LETTER_QUEUE).build();
    }

    @Bean
    public Binding urlAccessedDeadLetterBinding(
            Queue urlAccessedDeadLetterQueue, DirectExchange urlEventsDeadLetterExchange) {
        return BindingBuilder.bind(urlAccessedDeadLetterQueue)
                .to(urlEventsDeadLetterExchange)
                .with(URL_ACCESSED_ROUTING_KEY);
    }
}
