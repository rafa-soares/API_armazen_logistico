package com.wms.receiving.core.gateway;

import com.wms.receiving.core.domain.InboundDomain;

import java.util.List;

public interface InboundGateway {
    InboundDomain saveInbound(InboundDomain inboundDomain);

    List<InboundDomain> findAllInbounds();

    InboundDomain findFirstInbound();

    InboundDomain findInboundByCode(String code);
}
