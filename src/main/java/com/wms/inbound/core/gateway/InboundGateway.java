package com.wms.inbound.core.gateway;

import com.wms.inbound.core.domain.InboundDomain;

import java.util.List;

public interface InboundGateway {
    InboundDomain save(final InboundDomain inboundDomain);

    List<InboundDomain> findAllById(List<String> ids);
}
