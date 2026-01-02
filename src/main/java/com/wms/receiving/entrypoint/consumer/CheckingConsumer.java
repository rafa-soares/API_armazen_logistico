package com.wms.receiving.entrypoint.consumer;

import com.wms.receiving.infra.config.RabbitMQConfig;
import com.wms.receiving.infra.listener.event.InboundReceivedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(
        name = "app.rabbit.consumers.enabled",
        havingValue = "true",
        matchIfMissing = false
)
@Slf4j
@Component
public class CheckingConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void consumer(@Payload InboundReceivedEvent message) {
        log.info("[consumer] Event consumed in checking, message= {}", message);
    }
}
