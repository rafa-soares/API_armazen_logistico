package com.wms.receiving.infra.gateway;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.core.gateway.AppointmentGateway;
import com.wms.receiving.infra.model.Inbound;
import com.wms.receiving.infra.repository.InboundRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class AppointmentGatewayImp implements AppointmentGateway {
    private final InboundRepository inboundRepository;

    @Override
    public InboundDomain saveInbound(final InboundDomain inboundDomain) {
        final Inbound inbound = InboundDomain.toInbound(inboundDomain);
        final Inbound inboundReturn = inboundRepository.save(inbound);
        log.info("[saveInbound] Inbound saved. {}", inboundReturn);
        return Inbound.toDomain(inboundReturn);
    }
}