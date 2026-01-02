package com.wms.receiving.entrypoint.mapper.domainToResponse;

import com.wms.receiving.core.domain.ItemDomain;
import com.wms.receiving.entrypoint.controller.dtos.ItemResponseDTO;

public interface ItemDomainToItemResponseConverter {
    ItemResponseDTO toResponse(final ItemDomain itemDomain);
}
