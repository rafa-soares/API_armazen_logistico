package com.wms.receiving.core.usecase;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.core.gateway.AppointmentGateway;
import com.wms.receiving.entrypoint.controller.dtos.InboundRequestDTO;
import com.wms.receiving.entrypoint.controller.dtos.InboundResponseDTO;
import com.wms.receiving.entrypoint.mapper.domainToResponse.InboundDomainToInboundResponseConverter;
import com.wms.receiving.entrypoint.mapper.requestToDomain.InboundRequestToInboundDomainConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CreateInbound {
    private final AppointmentGateway appointmentGateway;
    private final InboundRequestToInboundDomainConverter inboundRequestConverter;
    private final InboundDomainToInboundResponseConverter inboundDomainConverter;

    public InboundResponseDTO execute(final InboundRequestDTO inboundRequestDTO) {
        final InboundDomain inboundDomain = inboundRequestConverter.toDomain(inboundRequestDTO);
        return inboundDomainConverter.toResponse(appointmentGateway.saveInbound(inboundDomain));
    }
}
