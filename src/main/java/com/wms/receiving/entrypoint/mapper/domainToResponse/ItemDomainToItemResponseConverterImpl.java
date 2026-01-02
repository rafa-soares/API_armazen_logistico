package com.wms.receiving.entrypoint.mapper.domainToResponse;

import com.wms.receiving.core.domain.ItemDomain;
import com.wms.receiving.entrypoint.controller.dtos.ItemResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ItemDomainToItemResponseConverterImpl implements ItemDomainToItemResponseConverter {

    @Override
    public ItemResponseDTO toResponse(ItemDomain itemDomain) {
        final ItemResponseDTO itemResponseDTO = new ItemResponseDTO();
        itemResponseDTO.setId(itemDomain.getId());
        itemResponseDTO.setDescription(itemDomain.getDescription());
        itemResponseDTO.setSku(itemDomain.getSku());
        itemResponseDTO.setQty(itemDomain.getQty());
        itemResponseDTO.setStatusChecking(itemDomain.getStatusChecking());
        return itemResponseDTO;
    }
}
