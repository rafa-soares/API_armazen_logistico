package com.wms.appointment.infra.gateway;

import com.wms.appointment.core.domain.AppointmentDomain;
import com.wms.appointment.core.gateway.AppointmentGateway;
import com.wms.appointment.infra.mapper.AppointmentMapper;
import com.wms.appointment.infra.model.Appointment;
import com.wms.appointment.infra.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class AppointmentGatewayImp implements AppointmentGateway {
    public final AppointmentRepository appointmentRepository;
    public final AppointmentMapper appointmentMapper;

    @Override
    public AppointmentDomain save(AppointmentDomain appointmentDomain) {
        log.info("[save] Saving appointment. {}", appointmentDomain);
        final Appointment appointment = appointmentMapper.toEntity(appointmentDomain);

        final Appointment appointmentResult = appointmentRepository.save(appointment);
        log.info("[save] Appointment save. {}", appointmentResult);

        return appointmentMapper.toDomain(appointmentResult);
    }
}