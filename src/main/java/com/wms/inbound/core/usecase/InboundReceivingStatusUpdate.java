package com.wms.inbound.core.usecase;

import com.wms.inbound.core.domain.InboundDomain;
import com.wms.inbound.entrypoint.controller.dtos.InboundResponseDTO;
import com.wms.inbound.infra.mapper.InboundMapper;
import com.wms.inbound.core.gateway.ReceivingGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Service
public class InboundReceivingStatusUpdate {
    private final ReceivingGateway receivedGateway;
    private final InboundMapper inboundMapper;

    @Transactional
    public InboundResponseDTO execute(final String inboundId) {
        final InboundDomain inboundDomain = receivedGateway.updateStatusInbound(inboundId);

        return InboundResponseDTO.builder()
                .id(inboundDomain.getId())
                .status(inboundDomain.getStatus())
                .build();
    }
}
