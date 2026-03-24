package com.wms.inbound.core.gateway;

import com.wms.inbound.core.domain.AppointmentDomain;

public interface AppointmentGateway {
    AppointmentDomain save(final AppointmentDomain appointmentDomain);

    AppointmentDomain findById(final String appointmentId);
}
