package com.wms.appointment.core.gateway;

import com.wms.appointment.core.domain.AppointmentDomain;

public interface AppointmentGateway {
    AppointmentDomain save(AppointmentDomain appointmentDomain);
}
