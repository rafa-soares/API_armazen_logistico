package com.wms.receiving.infra.mapper.domainToModel;

import com.wms.receiving.core.domain.ItemDomain;
import com.wms.receiving.infra.model.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemDomainToItemConverterImpl implements ItemDomainToItemConverter {
    @Override
    public Item toItem(ItemDomain itemDomain) {
        final Item item = new Item();
        item.setId();
        item.setDescription(itemDomain.getDescription());
        item.setSku(itemDomain.getSku());
        item.setQty(itemDomain.getQty());
        return item;
    }
}
