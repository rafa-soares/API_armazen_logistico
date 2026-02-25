package com.wms.appointment.core.usecase;

import com.wms.appointment.core.domain.ItemDomain;
import com.wms.appointment.core.gateway.ItemGateway;
import com.wms.appointment.entrypoint.controller.dtos.ItemRequestDTO;
import com.wms.appointment.entrypoint.controller.dtos.ItemResponseDTO;
import com.wms.appointment.infra.mapper.ItemMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CreateItem {
    public final ItemGateway itemGateway;
    public final ItemMapper itemMapper;

    public ItemResponseDTO execute(final ItemRequestDTO itemRequest) {
        final ItemDomain itemDomain = itemMapper.toDomain(itemRequest);
        return itemMapper.toResponse(itemGateway.save(itemDomain));
    }
}
