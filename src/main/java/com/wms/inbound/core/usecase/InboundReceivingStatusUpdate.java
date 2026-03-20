package com.wms.inbound.core.usecase;

import com.wms.inbound.core.domain.InboundDomain;
import com.wms.inbound.core.gateway.InboundGateway;
import com.wms.inbound.core.gateway.ReceivingGateway;
import com.wms.inbound.entrypoint.controller.dtos.InboundResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Service
public class InboundReceivingStatusUpdate {
    private final InboundGateway inboundGateway;
    private final ReceivingGateway receivedGateway;

    @Transactional
    public InboundResponseDTO execute(final String inboundId) {
        receivedGateway.updateStatusInbound(inboundId);

        final InboundDomain inboundReturn = inboundGateway.findById(inboundId);

        return InboundResponseDTO.builder()
                .id(inboundReturn.getId())
                .status(inboundReturn.getStatus())
                .build();
    }
}