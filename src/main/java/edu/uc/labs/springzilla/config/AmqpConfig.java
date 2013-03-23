package edu.uc.labs.springzilla.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public interface AmqpConfig {
    RabbitTemplate rabbitTemplate();
    ConnectionFactory connectionFactory();
    AmqpAdmin amqpAdmin();
    Queue multicastQueue();
}
