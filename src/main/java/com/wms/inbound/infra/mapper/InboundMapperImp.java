package com.wms.inbound.infra.mapper;

import com.wms.inbound.core.domain.InboundDomain;
import com.wms.inbound.core.domain.ItemDomain;
import com.wms.inbound.entrypoint.controller.dtos.InboundResponseDTO;
import com.wms.inbound.entrypoint.controller.dtos.ItemResponseDTO;
import com.wms.inbound.infra.model.Inbound;
import com.wms.inbound.infra.model.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class InboundMapperImp implements InboundMapper{
    public final ItemMapper itemMapper;

    @Override
    public InboundDomain toDomain(List<ItemDomain> itemsDomain) {
        return new InboundDomain(null, null, itemsDomain);
    }

    @Override
    public Inbound toEntity(List<Item> items) {
        final Inbound inbound = new Inbound(items);
        return inbound;
    }

    @Override
    public InboundDomain toDomain(Inbound inbound) {
        final List<ItemDomain> itemDomain = inbound.getItems().stream()
                .map(item -> itemMapper.toDomain(item))
                .toList();

        return new InboundDomain(inbound.getId().toString(), inbound.getStatus().toString(), itemDomain);
    }

    @Override
    public InboundResponseDTO toResponse(InboundDomain inboundDomain) {
        final List<ItemResponseDTO> itemsResponse = inboundDomain.getItems().stream()
                .map(itemDomain -> itemMapper.toResponse(itemDomain))
                .toList();

        return new InboundResponseDTO(inboundDomain.getId(), inboundDomain.getStatus(), itemsResponse);
    }

    @Override
    public Inbound toEntity(InboundDomain inboundDomain) {
        final List<Item> items = itemMapper.toEntity(inboundDomain.getItems());

        return new Inbound(items);
    }
}
