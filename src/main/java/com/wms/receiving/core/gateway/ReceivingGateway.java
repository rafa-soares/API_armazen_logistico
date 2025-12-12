package com.wms.receiving.core.gateway;

import com.wms.receiving.core.domain.InboundDomain;

import java.util.List;

public interface ReceivingGateway {
    List<InboundDomain> findAllInbounds();

    InboundDomain findFirstInbound();

    InboundDomain findInboundByCode(final String code);

    InboundDomain updateStatusInbound(final Long inboundId);
}
