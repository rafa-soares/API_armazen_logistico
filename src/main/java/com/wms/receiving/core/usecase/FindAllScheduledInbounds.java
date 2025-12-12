package com.wms.receiving.core.usecase;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.entrypoint.controller.dtos.InboundResponseDTO;
import com.wms.receiving.infra.gateway.ReceivingGatewayImp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class FindAllScheduledInbounds {
    private ReceivingGatewayImp receivingGatewayImp;

    public List<InboundResponseDTO> execute() {
        final List<InboundDomain> inboundsDomain = receivingGatewayImp.findAllInbounds();
        return InboundDomain.toResponse(inboundsDomain);
    }
}
