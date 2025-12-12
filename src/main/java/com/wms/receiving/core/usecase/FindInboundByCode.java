package com.wms.receiving.core.usecase;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.entrypoint.controller.dtos.InboundResponseDTO;
import com.wms.receiving.infra.gateway.ReceivingGatewayImp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FindInboundByCode {
    private ReceivingGatewayImp receivingGatewayImp;

    public InboundResponseDTO execute(final String code) {
        return InboundDomain.toResponse(receivingGatewayImp.findInboundByCode(code));
    }
}