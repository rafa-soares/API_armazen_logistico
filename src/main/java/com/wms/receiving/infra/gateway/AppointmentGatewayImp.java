package com.wms.receiving.infra.gateway;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.core.gateway.AppointmentGateway;
import com.wms.receiving.infra.mapper.domainToModel.InboundDomainToInboundConverter;
import com.wms.receiving.infra.mapper.modelToDomain.InboundToInboundDomainConverter;
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
    private final InboundDomainToInboundConverter inboundDomainConverter;
    private final InboundToInboundDomainConverter inboundConverter;

    @Override
    public InboundDomain saveInbound(final InboundDomain inboundDomain) {
        final Inbound inbound = inboundDomainConverter.toInbound(inboundDomain);
        final Inbound inboundReturn = inboundRepository.save(inbound);
        log.info("[saveInbound] Inbound saved. {}", inboundReturn);
        return this.inboundConverter.toDomain(inboundReturn);
    }
}