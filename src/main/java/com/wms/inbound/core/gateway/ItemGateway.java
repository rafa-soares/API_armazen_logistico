package com.wms.inbound.core.gateway;

import com.wms.inbound.core.domain.ItemDomain;

import java.util.List;

public interface ItemGateway {
    ItemDomain save(ItemDomain itemDomain);

    List<ItemDomain> findAllById(List<String> ids);
}
