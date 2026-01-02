package com.wms.receiving.entrypoint.mapper.domainToResponse;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.entrypoint.controller.dtos.InboundResponseDTO;

import java.util.List;

public interface InboundDomainToInboundResponseConverter {
    InboundResponseDTO toResponse(final InboundDomain inboundDomain);

    List<InboundResponseDTO> toResponse(final List<InboundDomain> inboundsDomain);
}
