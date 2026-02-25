package com.wms.appointment.infra.gateway;

import com.wms.appointment.core.domain.InboundDomain;
import com.wms.appointment.core.gateway.InboundGateway;
import com.wms.appointment.infra.mapper.InboundMapper;
import com.wms.appointment.infra.model.Inbound;
import com.wms.appointment.infra.repository.InboundRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class InboundGatewayImp implements InboundGateway {
    public final InboundRepository inboundRepository;
    public final InboundMapper inboundMapper;

    @Override
    public InboundDomain save(InboundDomain inboundDomain) {
        log.info("[save] Saving inbound. {}", inboundDomain);
        final Inbound inbound = inboundMapper.toEntity(inboundDomain);

        final Inbound inboundResult = inboundRepository.save(inbound);
        log.info("[save] Inbound save. {}", inboundResult);

        return inboundMapper.toDomain(inboundResult);
    }
}
