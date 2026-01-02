package com.wms.receiving.entrypoint.mapper.requestToDomain;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.entrypoint.controller.dtos.InboundRequestDTO;

public interface InboundRequestToInboundDomainConverter {
    InboundDomain toDomain(final InboundRequestDTO inboundRequestDTO);
}
