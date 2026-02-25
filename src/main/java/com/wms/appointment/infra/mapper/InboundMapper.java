package com.wms.appointment.infra.mapper;

import com.wms.appointment.core.domain.InboundDomain;
import com.wms.appointment.entrypoint.controller.dtos.InboundRequestDTO;
import com.wms.appointment.entrypoint.controller.dtos.InboundResponseDTO;
import com.wms.appointment.infra.model.Inbound;

public interface InboundMapper {
    InboundDomain toDomain(InboundRequestDTO inboundRequest);

    Inbound toEntity(InboundDomain inboundDomain);

    InboundDomain toDomain(Inbound inbound);

    InboundResponseDTO toResponse(InboundDomain inboundDomain);
}
