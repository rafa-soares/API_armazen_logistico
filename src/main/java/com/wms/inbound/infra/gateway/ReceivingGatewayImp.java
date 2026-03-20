package com.wms.inbound.infra.gateway;

import com.wms.inbound.core.exceptions.InboundAlreadyReceivedException;
import com.wms.inbound.core.exceptions.InboundNotFoundException;
import com.wms.inbound.core.exceptions.InvalidInboundStatusException;
import com.wms.inbound.core.gateway.ReceivingGateway;
import com.wms.inbound.infra.model.Inbound;
import com.wms.inbound.infra.model.StatusInbound;
import com.wms.inbound.infra.repository.ReceivingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class ReceivingGatewayImp implements ReceivingGateway {
    private final ReceivingRepository receivingRepository;

    @Override
    public void updateStatusInbound(final String inboundId) {
        final UUID uuid = UUID.fromString(inboundId);

        final int rows = receivingRepository.updateStatus(uuid, StatusInbound.RECEIVED);

        if (rows == 0) {
            Inbound inbound = receivingRepository.findById(uuid)
                    .orElseThrow(() -> new InboundNotFoundException(uuid));

            if (StatusInbound.RECEIVED == inbound.getStatus()) {
                throw new InboundAlreadyReceivedException();
            }

            throw new InvalidInboundStatusException(inbound.getStatus().toString());
        }
    }
}