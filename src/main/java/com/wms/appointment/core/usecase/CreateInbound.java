package com.wms.appointment.core.usecase;

import com.wms.appointment.core.domain.InboundDomain;
import com.wms.appointment.core.gateway.InboundGateway;
import com.wms.appointment.entrypoint.controller.dtos.InboundRequestDTO;
import com.wms.appointment.entrypoint.controller.dtos.InboundResponseDTO;
import com.wms.appointment.infra.mapper.InboundMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CreateInbound {
    public final InboundGateway inboundGateway;
    public final InboundMapper inboundMapper;

    public InboundResponseDTO execute(final InboundRequestDTO inboundRequest) {
        final InboundDomain inboundDomain = inboundMapper.toDomain(inboundRequest);
        return inboundMapper.toResponse(inboundGateway.save(inboundDomain));
    }
}
