package com.wms.appointment.infra.mapper;

import com.wms.appointment.core.domain.InboundDomain;
import com.wms.appointment.core.domain.ItemDomain;
import com.wms.appointment.entrypoint.controller.dtos.InboundResponseDTO;
import com.wms.appointment.infra.model.Inbound;
import com.wms.appointment.infra.model.Item;

import java.util.List;

public interface InboundMapper {
    InboundDomain toDomain(List<ItemDomain> itemDomain);

    Inbound toEntity(List<Item> items);

    InboundDomain toDomain(Inbound inbound);

    InboundResponseDTO toResponse(InboundDomain inboundDomain);

    Inbound toEntity(InboundDomain inboundDomain);
}