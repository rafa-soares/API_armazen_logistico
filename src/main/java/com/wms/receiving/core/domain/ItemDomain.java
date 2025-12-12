package com.wms.receiving.core.domain;

import com.wms.receiving.entrypoint.controller.dtos.ItemResponseDTO;
import com.wms.receiving.infra.model.Item;
import com.wms.receiving.infra.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDomain {
    private Long id;

    private String description;

    private String sku;

    private Integer qty;

    private Status statusChecking;

    public static Item toItem(final ItemDomain itemDomain) {
        final Item item = new Item();
        item.setId();
        item.setDescription(itemDomain.description);
        item.setSku(itemDomain.sku);
        item.setQty(itemDomain.qty);
        return item;
    }

    public static ItemResponseDTO toResponse(final ItemDomain itemDomain) {
        final ItemResponseDTO itemResponseDTO = new ItemResponseDTO();
        itemResponseDTO.setId(itemDomain.id);
        itemResponseDTO.setDescription(itemDomain.description);
        itemResponseDTO.setSku(itemDomain.sku);
        itemResponseDTO.setQty(itemDomain.qty);
        itemResponseDTO.setStatusChecking(itemDomain.statusChecking);
        return itemResponseDTO;
    }
}
