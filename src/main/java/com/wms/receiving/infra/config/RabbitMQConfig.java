package com.wms.receiving.infra.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@AllArgsConstructor
@Configuration
public class RabbitMQConfig {
    public static final String QUEUE = "receiving.queue";
    public static final String EXCHANGE = "receiving.exchange";
    public static final String ROUTING_KEY = "receiving.key";

    @Bean
    public Queue receivingQueue() {
        return new Queue(QUEUE, true);
    }

    @Bean
    public DirectExchange receivingExchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding receivingBinding() {
        return BindingBuilder
                .bind(receivingQueue())
                .to(receivingExchange())
                .with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter jacksonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            MessageConverter messageConverter) {

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}