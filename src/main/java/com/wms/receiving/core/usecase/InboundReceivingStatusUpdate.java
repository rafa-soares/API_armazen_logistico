package com.wms.receiving.core.usecase;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.core.gateway.ReceivingGateway;
import com.wms.receiving.entrypoint.controller.dtos.InboundResponseDTO;
import com.wms.receiving.entrypoint.mapper.domainToResponse.InboundDomainToInboundResponseConverter;
import com.wms.receiving.infra.listener.event.InboundReceivedEvent;
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
    private final InboundDomainToInboundResponseConverter inboundDomainConverter;

    public InboundResponseDTO execute(final Long inbound) {
        InboundDomain inboundDomain = receivingGateway.updateStatusInbound(inbound);
        log.info("[execute] Inbound status updated successfully.");

        log.info("[execute] Publishing the event to RabbitMQ inboundDomain= {}", inboundDomain.toString());
        publisher.publishEvent(new InboundReceivedEvent(
                inboundDomain.getId(),
                inboundDomain.getStatusReceiving().toString(),
                inboundDomain.getItems()));

        return inboundDomainConverter.toResponse(inboundDomain);
    }
}