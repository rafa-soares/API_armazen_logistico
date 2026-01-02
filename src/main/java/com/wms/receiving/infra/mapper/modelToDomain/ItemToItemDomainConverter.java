package com.wms.receiving.infra.mapper.modelToDomain;

import com.wms.receiving.core.domain.ItemDomain;
import com.wms.receiving.infra.model.Item;
import org.springframework.stereotype.Component;

@Component
public interface ItemToItemDomainConverter {
    ItemDomain toDomain(final Item item);
}
