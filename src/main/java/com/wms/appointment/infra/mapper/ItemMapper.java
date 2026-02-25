package com.wms.appointment.infra.mapper;

import com.wms.appointment.core.domain.ItemDomain;
import com.wms.appointment.entrypoint.controller.dtos.ItemRequestDTO;
import com.wms.appointment.entrypoint.controller.dtos.ItemResponseDTO;
import com.wms.appointment.infra.model.Item;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    ItemDomain toDomain(ItemRequestDTO itemRequest);

    Item toEntity(ItemDomain itemDomain);

    ItemDomain toDomain(Item item);

    ItemResponseDTO toResponse(ItemDomain itemDomain);

    List<ItemResponseDTO> toResponse(List<ItemDomain> itemsDomain);

    //

    List<ItemDomain> toDomain(List<ItemRequestDTO> itemsRequest);

    List<Item> toEntity(List<ItemDomain> itemsDomains);

    List<ItemDomain> toDomains(List<Item> items);
}