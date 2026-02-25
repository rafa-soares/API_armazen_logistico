package com.wms.appointment.core.gateway;

import com.wms.appointment.core.domain.ItemDomain;

import java.util.List;

public interface ItemGateway {
    ItemDomain save(ItemDomain itemDomain);

    List<ItemDomain> findAllById(List<String> ids);
}
