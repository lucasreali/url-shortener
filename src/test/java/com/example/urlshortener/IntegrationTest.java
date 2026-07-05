package com.example.urlshortener;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.rabbitmq.RabbitMQContainer;

@SpringBootTest
public abstract class IntegrationTest {

    @ServiceConnection
    static final PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:18-alpine");

    @ServiceConnection(name = "redis")
    static final GenericContainer<?> valkey =
            new GenericContainer<>("valkey/valkey:9-alpine").withExposedPorts(6379);

    @ServiceConnection
    static final RabbitMQContainer rabbit = new RabbitMQContainer("rabbitmq:4-alpine");

    static {
        Startables.deepStart(postgres, valkey, rabbit).join();
    }
}
