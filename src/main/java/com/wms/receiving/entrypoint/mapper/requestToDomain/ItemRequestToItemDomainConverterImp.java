package com.wms.receiving.entrypoint.mapper.requestToDomain;

import com.wms.receiving.core.domain.ItemDomain;
import com.wms.receiving.entrypoint.controller.dtos.ItemRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ItemRequestToItemDomainConverterImp implements ItemRequestToItemDomainConverter {
    @Override
    public ItemDomain toDomain(ItemRequestDTO itemRequestDTO) {
        final ItemDomain itemDomain = new ItemDomain();
        itemDomain.setDescription(itemRequestDTO.getDescription());
        itemDomain.setSku(itemRequestDTO.getSku());
        itemDomain.setQty(itemRequestDTO.getQty());
        return itemDomain;
    }
}
