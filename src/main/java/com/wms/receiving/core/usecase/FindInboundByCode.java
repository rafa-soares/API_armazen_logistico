package com.wms.receiving.core.usecase;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.core.gateway.InboundGateway;
import com.wms.receiving.entrypoint.dtos.InboundResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FindInboundByCode {
    private final InboundGateway inboundGateway;

    public InboundResponseDTO execute(final String code) {
        return InboundDomain.toResponse(inboundGateway.findInboundByCode(code));
    }
}