package com.wms.receiving.core.usecase;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.core.gateway.InboundGateway;
import com.wms.receiving.entrypoint.dtos.InboundResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class FindAllScheduledInbounds {
    private final InboundGateway inboundGateway;

    public List<InboundResponseDTO> execute() {
        final List<InboundDomain> inboundsDomain = inboundGateway.findAllInbounds();
        return InboundDomain.toResponse(inboundsDomain);
    }
}
