package com.wms.inbound.core.gateway;

import com.wms.inbound.core.domain.InboundDomain;

public interface ReceivingGateway {
    void updateStatusInbound(String inboundId);
}
