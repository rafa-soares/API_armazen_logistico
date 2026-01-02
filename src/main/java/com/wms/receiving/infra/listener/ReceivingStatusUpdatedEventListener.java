package com.wms.receiving.infra.listener;

import com.wms.receiving.infra.listener.event.InboundReceivedEvent;
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
    public void listenen(final InboundReceivedEvent inboundEvent) {
        log.info("[listenen] inboundEvent= {}", inboundEvent);
        rabbitTemplate.convertAndSend("receiving.exchange", "receiving.key", inboundEvent);
        log.info("Event published to RabbitMQ successfully.");
    }
}