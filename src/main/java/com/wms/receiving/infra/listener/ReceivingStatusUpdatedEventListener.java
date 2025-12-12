package com.wms.receiving.infra.listener;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class ReceivingStatusUpdatedEventListener {
    private final RabbitTemplate rabbitTemplate;

    @Async
    @EventListener
    public void listenen(final String receivingStatus) {
        log.info("Publishing the event to RabbitMQ receivingStatus={}", receivingStatus);
        rabbitTemplate.convertAndSend("receiving.exchange", "receiving.key", receivingStatus);
        log.info("Event published to RabbitMQ successfully.");
    }
}