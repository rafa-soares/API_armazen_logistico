package com.wms.inbound.infra.mapper;

import com.wms.inbound.core.domain.InboundDomain;
import com.wms.inbound.core.domain.ItemDomain;
import com.wms.inbound.entrypoint.controller.dtos.InboundResponseDTO;
import com.wms.inbound.infra.model.Inbound;
import com.wms.inbound.infra.model.Item;

import java.util.List;

public interface InboundMapper {
    InboundDomain toDomain(List<ItemDomain> itemDomain);

    Inbound toEntity(List<Item> items);

    InboundDomain toDomain(Inbound inbound);

    InboundResponseDTO toResponse(InboundDomain inboundDomain);

    Inbound toEntity(InboundDomain inboundDomain);
}