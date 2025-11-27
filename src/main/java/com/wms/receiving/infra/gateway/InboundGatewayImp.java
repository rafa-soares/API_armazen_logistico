package com.wms.receiving.infra.gateway;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.core.gateway.InboundGateway;
import com.wms.receiving.infra.model.Inbound;
import com.wms.receiving.infra.repository.InboundRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class InboundGatewayImp implements InboundGateway {
    private final InboundRepository inboundRepository;

    @Override
    public InboundDomain saveInbound(final InboundDomain inboundDomain) {
        final Inbound inbound = InboundDomain.toInbound(inboundDomain);
        final Inbound inboundReturn = inboundRepository.save(inbound);
        log.info("[saveInbound] Inbound saved. {}", inboundReturn);
        return Inbound.toDomain(inboundReturn);
    }

    @Override
    public List<InboundDomain> findAllInbounds() {
        final List<Inbound> inbounds = inboundRepository.findAll();
        log.info("[findAllInbounds] List of all inbounds to be received. {}", inbounds);
        return Inbound.toDomain(inbounds);
    }

    @Override
    public InboundDomain findFirstInbound() {
        final Inbound inbound = inboundRepository.findAll().stream()
                .findFirst()
                .orElseThrow();
        log.info("[findFirstInbound] First inbound to be received. {}", inbound);
        return Inbound.toDomain(inbound);
    }

    @Override
    public InboundDomain findInboundByCode(final String code) {
        final Inbound inbound = inboundRepository.findInboundByCode(code);
        log.info("[findInboundByCode] Get inboundId by code={}", code);
        return Inbound.toDomain(inbound);
    }
}