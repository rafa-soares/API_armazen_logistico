package com.wms.receiving.core.usecase;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.entrypoint.controller.dtos.InboundResponseDTO;
import com.wms.receiving.entrypoint.mapper.domainToResponse.InboundDomainToInboundResponseConverter;
import com.wms.receiving.infra.gateway.ReceivingGatewayImp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class FindAllScheduledInbounds {
    private final ReceivingGatewayImp receivingGatewayImp;
    private final InboundDomainToInboundResponseConverter inboundDomainConverter;

    public List<InboundResponseDTO> execute() {
        final List<InboundDomain> inboundsDomain = receivingGatewayImp.findAllInbounds();
        return inboundDomainConverter.toResponse(inboundsDomain);
    }
}
