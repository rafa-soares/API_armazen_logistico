package com.wms.receiving.infra.mapper.domainToModel;

import com.wms.receiving.core.domain.ItemDomain;
import com.wms.receiving.infra.model.Item;

public interface ItemDomainToItemConverter {
    Item toItem(final ItemDomain itemDomain);
}
