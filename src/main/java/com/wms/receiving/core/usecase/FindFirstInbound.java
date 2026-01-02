package com.wms.receiving.core.usecase;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.entrypoint.controller.dtos.InboundResponseDTO;
import com.wms.receiving.entrypoint.mapper.domainToResponse.InboundDomainToInboundResponseConverter;
import com.wms.receiving.infra.gateway.ReceivingGatewayImp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FindFirstInbound {
    private final ReceivingGatewayImp receivingGatewayImp;
    private final InboundDomainToInboundResponseConverter inboundDomainConverter;

    public InboundResponseDTO execute() {
        InboundDomain inboundDomain = receivingGatewayImp.findFirstInbound();
        return inboundDomainConverter.toResponse(inboundDomain);
    }
}
