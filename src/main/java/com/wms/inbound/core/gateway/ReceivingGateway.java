package com.wms.inbound.core.gateway;

import com.wms.inbound.core.domain.InboundDomain;

public interface ReceivingGateway {
    InboundDomain updateStatusInbound(String inboundId);
}
