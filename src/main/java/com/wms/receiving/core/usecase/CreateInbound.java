package com.wms.receiving.core.usecase;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.core.gateway.AppointmentGateway;
import com.wms.receiving.entrypoint.controller.dtos.InboundRequestDTO;
import com.wms.receiving.entrypoint.controller.dtos.InboundResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CreateInbound {
    private final AppointmentGateway appointmentGateway;

    public InboundResponseDTO execute(final InboundRequestDTO inboundRequestDTO) {
        final InboundDomain inboundDomain = InboundRequestDTO.toDomain(inboundRequestDTO);
        return InboundDomain.toResponse(appointmentGateway.saveInbound(inboundDomain));
    }
}
