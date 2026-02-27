package com.wms.appointment.infra.mapper;

import com.wms.appointment.core.domain.InboundDomain;
import com.wms.appointment.core.domain.ItemDomain;
import com.wms.appointment.entrypoint.controller.dtos.InboundRequestDTO;
import com.wms.appointment.entrypoint.controller.dtos.InboundResponseDTO;
import com.wms.appointment.entrypoint.controller.dtos.ItemResponseDTO;
import com.wms.appointment.infra.model.Inbound;
import com.wms.appointment.infra.model.Item;
import com.wms.appointment.infra.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class InboundMapperImp implements InboundMapper{
    public final ItemRepository itemRepository;
    public final ItemMapper itemMapper;

    @Override
    public InboundDomain toDomain(InboundRequestDTO inboundRequest) {
        return null;
    }

    @Override
    public InboundDomain toDomain(List<ItemDomain> itemsDomain) {
        return new InboundDomain(null, null, itemsDomain);
    }

    @Override
    public Inbound toEntity(InboundDomain inboundDomain) {
        final List<Item> items = inboundDomain.getItems().stream()
                .map(itemDomain -> itemRepository.getReferenceById(UUID.fromString(itemDomain.getId())))
                .toList();

        return new Inbound(items);
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
//        final List<ItemResponseDTO> itemsResponse = itemMapper.toResponse(inboundDomain.getItems());
        final List<ItemResponseDTO> itemsResponse = inboundDomain.getItems().stream()
                .map(itemDomain -> itemMapper.toResponse(itemDomain))
                .toList();

        return new InboundResponseDTO(inboundDomain.getId(), inboundDomain.getStatus(), itemsResponse);
    }

//    @Override
//    public List<InboundDomain> toDomains(List<Inbound> inbounds) {
//        if (inbounds == null ) {
//            return null;
//        } else {
//            List<InboundDomain> list = new ArrayList<>(inbounds.size());
//
//            for (Inbound inbound : inbounds) {
//                list.add(this.toDomain(inbound));
//            }
//            return list;
//        }
//    }
}
