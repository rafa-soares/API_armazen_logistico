package com.wms.receiving.core.usecase;

import com.wms.receiving.entrypoint.controller.dtos.InboundResponseDTO;
import com.wms.receiving.entrypoint.mapper.domainToResponse.InboundDomainToInboundResponseConverter;
import com.wms.receiving.infra.gateway.ReceivingGatewayImp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FindInboundByCode {
    private final ReceivingGatewayImp receivingGatewayImp;
    private final InboundDomainToInboundResponseConverter inboundDomainConverter;

    public InboundResponseDTO execute(final String code) {
        return inboundDomainConverter.toResponse(receivingGatewayImp.findInboundByCode(code));
    }
}