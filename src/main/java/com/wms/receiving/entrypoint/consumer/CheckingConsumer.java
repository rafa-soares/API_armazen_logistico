package com.wms.receiving.entrypoint.consumer;

import com.wms.receiving.infra.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CheckingConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void consumer(@Payload Message message) {
        log.info("CheckingConsumer received status: {}", message);
    }
}
