package com.wms.inbound.core.usecase;

import com.wms.inbound.core.domain.ItemDomain;
import com.wms.inbound.core.gateway.ItemGateway;
import com.wms.inbound.entrypoint.controller.dtos.ItemRequestDTO;
import com.wms.inbound.entrypoint.controller.dtos.ItemResponseDTO;
import com.wms.inbound.infra.mapper.ItemMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class CreateItem {
    public final ItemGateway itemGateway;
    public final ItemMapper itemMapper;

    public ItemResponseDTO execute(final ItemRequestDTO itemRequest) {
        final ItemDomain itemDomain = itemMapper.toDomain(itemRequest);

        final ItemDomain itemResult = itemGateway.save(itemDomain);
        log.info("[execute] Item save. {}", itemResult);

        return itemMapper.toResponse(itemResult);
    }
}
