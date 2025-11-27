package com.wms.receiving.core.usecase;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.core.gateway.InboundGateway;
import com.wms.receiving.entrypoint.dtos.InboundResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FindFirstInbound {
    private InboundGateway inboundGateway;

    public InboundResponseDTO execute() {
        InboundDomain inboundDomain = inboundGateway.findFirstInbound();
        return InboundDomain.toResponse(inboundDomain);
    }
}
