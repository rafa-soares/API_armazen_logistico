package com.wms.inbound.core.usecase;

import com.wms.inbound.core.domain.InboundDomain;
import com.wms.inbound.core.domain.ItemDomain;
import com.wms.inbound.core.gateway.InboundGateway;
import com.wms.inbound.core.gateway.ItemGateway;
import com.wms.inbound.entrypoint.controller.dtos.InboundRequestDTO;
import com.wms.inbound.entrypoint.controller.dtos.InboundResponseDTO;
import com.wms.inbound.infra.mapper.InboundMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class CreateInbound {
    public final InboundGateway inboundGateway;
    public final ItemGateway itemGateway;
    public final InboundMapper inboundMapper;

    @Transactional
    public InboundResponseDTO execute(final InboundRequestDTO inboundRequest) {
        final List<ItemDomain> itemDomain = itemGateway.findAllById(inboundRequest.items());

        final InboundDomain inboundDomain = inboundMapper.toDomain(itemDomain);

        final InboundDomain inboundResult = inboundGateway.save(inboundDomain);
        log.info("[execute] Inbound save. {}", inboundResult);

        return inboundMapper.toResponse(inboundResult);
    }
}