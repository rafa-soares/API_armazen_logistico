package com.wms.receiving.infra.gateway;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.core.gateway.ReceivingGateway;
import com.wms.receiving.entrypoint.exceptions.ExceptionMessage;
import com.wms.receiving.entrypoint.exceptions.InboundNotFoundException;
import com.wms.receiving.infra.mapper.modelToDomain.InboundToInboundDomainConverter;
import com.wms.receiving.infra.model.Inbound;
import com.wms.receiving.infra.model.Status;
import com.wms.receiving.infra.repository.InboundRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class ReceivingGatewayImp implements ReceivingGateway {
    private final InboundRepository inboundRepository;
    private final InboundToInboundDomainConverter inboundConverter;

    @Override
    public InboundDomain findFirstInbound() {
        final Inbound inbound = inboundRepository.findAll().stream()
                .findFirst()
                .orElseThrow();
        log.info("[findFirstInbound] First inbound to be received. {}", inbound);
        return inboundConverter.toDomain(inbound);
    }

    @Override
    public InboundDomain findInboundByCode(final String code) {
        final Inbound inbound = inboundRepository.findInboundByCode(code);
        log.info("[findInboundByCode] Get inboundId by code={}", code);
        return inboundConverter.toDomain(inbound);
    }

    @Override
    public List<InboundDomain> findAllInbounds() {
        final List<Inbound> inbounds = inboundRepository.findAll();
        log.info("[findAllInbounds] List of all inbounds to be received. {}", inbounds);
        return inboundConverter.toDomain(inbounds);
    }

    @Override
    public InboundDomain updateStatusInbound(final Long inboundId) {
        final Inbound inbound = inboundRepository.findById(inboundId)
                .orElseThrow(() -> new InboundNotFoundException(ExceptionMessage.ERROR_INBOUND_UPDATE_NOT_FOUND));

        inbound.setStatusReceiving(Status.RECEIVED);

        return inboundConverter.toDomain(inboundRepository.save(inbound));
    }
}