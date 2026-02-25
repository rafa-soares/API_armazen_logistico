package com.wms.appointment.core.gateway;

import com.wms.appointment.core.domain.InboundDomain;

public interface InboundGateway {
    InboundDomain save(final InboundDomain inboundDomain);
}
