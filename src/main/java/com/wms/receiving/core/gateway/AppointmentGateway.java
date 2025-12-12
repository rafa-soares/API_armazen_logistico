package com.wms.receiving.core.gateway;

import com.wms.receiving.core.domain.InboundDomain;

import java.util.List;

public interface AppointmentGateway {
    InboundDomain saveInbound(InboundDomain inboundDomain);
}
