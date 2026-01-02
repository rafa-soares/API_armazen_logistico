package com.wms.receiving.entrypoint.mapper.requestToDomain;

import com.wms.receiving.core.domain.ItemDomain;
import com.wms.receiving.entrypoint.controller.dtos.ItemRequestDTO;

public interface ItemRequestToItemDomainConverter {
    ItemDomain toDomain(final ItemRequestDTO itemRequestDTO);
}
