package com.wms.receiving.infra.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@AllArgsConstructor
@Configuration
public class RabbitMQConfig {
    private AmqpAdmin amqpAdmin;

    @Bean
    public Object createQueue() {
        Queue queue = new Queue("receiving.queue", true, false, false);
        log.info("Creating Queue: {}", queue.getName());

        String createdQueue = amqpAdmin.declareQueue(queue);
        log.info("Created Queue: {}", createdQueue);

        DirectExchange direct = new DirectExchange("receiving.exchange");
        log.info("Creating Direct Exchange: {}", direct.getName());

        amqpAdmin.declareExchange(direct);

        Binding binding = BindingBuilder.bind(queue)
                .to(direct)
                .with("receiving.key");

        amqpAdmin.declareBinding(binding);

        return binding;
    }
}