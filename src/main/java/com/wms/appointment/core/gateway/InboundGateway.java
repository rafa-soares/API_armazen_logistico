package com.wms.appointment.core.gateway;

import com.wms.appointment.core.domain.InboundDomain;

import java.util.List;

public interface InboundGateway {
    InboundDomain save(final InboundDomain inboundDomain);

    List<InboundDomain> findAllById(List<String> ids);
}
