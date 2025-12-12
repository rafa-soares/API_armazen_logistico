package com.wms.receiving.core.usecase;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.core.gateway.ReceivingGateway;
import com.wms.receiving.entrypoint.controller.dtos.InboundResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class InboundReceivingStatusUpdate {
    private final ReceivingGateway receivingGateway;
    private final ApplicationEventPublisher publisher;

    public InboundResponseDTO execute(final Long inbound) {
        InboundDomain inboundDomain = receivingGateway.updateStatusInbound(inbound);
        log.info("Inbound status updated successfully.");

        log.info("Publishing the event to RabbitMQ receivingStatus= {}", inboundDomain.getStatusReceiving().toString());
        publisher.publishEvent(inboundDomain.getStatusReceiving().toString());

        return InboundDomain.toResponse(inboundDomain);
    }
}