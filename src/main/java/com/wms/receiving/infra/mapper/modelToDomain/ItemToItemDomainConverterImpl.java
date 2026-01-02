package com.wms.receiving.infra.mapper.modelToDomain;

import com.wms.receiving.core.domain.ItemDomain;
import com.wms.receiving.infra.model.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemToItemDomainConverterImpl implements ItemToItemDomainConverter {

    @Override
    public ItemDomain toDomain(final Item item) {
        final ItemDomain itemDomain = new ItemDomain();
        itemDomain.setId(item.getId());
        itemDomain.setDescription(item.getDescription());
        itemDomain.setSku(item.getSku());
        itemDomain.setQty(item.getQty());
        itemDomain.setStatusChecking(item.getStatusChecking());
        return itemDomain;
    }
}
