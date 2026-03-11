package com.wms.inbound.infra.gateway;

import com.wms.inbound.core.domain.InboundDomain;
import com.wms.inbound.core.exceptions.InboundNotFoundException;
import com.wms.inbound.infra.mapper.InboundMapper;
import com.wms.inbound.infra.model.Inbound;
import com.wms.inbound.infra.model.StatusInbound;
import com.wms.inbound.core.gateway.ReceivingGateway;
import com.wms.inbound.infra.repository.ReceivingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class ReceivingGatewayImp implements ReceivingGateway {
    private final ReceivingRepository inboundRepository;
    private final InboundMapper inboundMapper;

    @Override
    public InboundDomain updateStatusInbound(final String inboundId) {
        final UUID uuid = UUID.fromString(inboundId);

        final Inbound inbound = inboundRepository.findById(uuid)
                .orElseThrow(() -> new InboundNotFoundException(uuid));

        inboundRepository.updateStatus(inbound.getId(), StatusInbound.RECEIVED);

        return new InboundDomain(
                inbound.getId().toString(),
                inbound.getStatus().toString());
    }
}
