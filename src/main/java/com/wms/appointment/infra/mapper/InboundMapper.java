package com.wms.appointment.infra.mapper;

import com.wms.appointment.core.domain.InboundDomain;
import com.wms.appointment.core.domain.ItemDomain;
import com.wms.appointment.entrypoint.controller.dtos.InboundRequestDTO;
import com.wms.appointment.entrypoint.controller.dtos.InboundResponseDTO;
import com.wms.appointment.infra.model.Inbound;

import java.util.List;

public interface InboundMapper {
    InboundDomain toDomain(InboundRequestDTO inboundRequest);

    Inbound toEntity(InboundDomain inboundDomain);

    InboundDomain toDomain(Inbound inbound);

    InboundResponseDTO toResponse(InboundDomain inboundDomain);

//    List<InboundDomain> toDomains(List<Inbound> inbounds);

    InboundDomain toDomain(List<ItemDomain> itemDomain);
}
