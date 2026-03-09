package com.wms.appointment.infra.gateway;

import com.wms.appointment.core.domain.AppointmentDomain;
import com.wms.appointment.core.gateway.AppointmentGateway;
import com.wms.appointment.infra.mapper.AppointmentMapper;
import com.wms.appointment.infra.model.Appointment;
import com.wms.appointment.infra.model.Inbound;
import com.wms.appointment.infra.model.Seller;
import com.wms.appointment.infra.repository.AppointmentRepository;
import com.wms.appointment.infra.repository.InboundRepository;
import com.wms.appointment.infra.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class AppointmentGatewayImp implements AppointmentGateway {
    public final AppointmentRepository appointmentRepository;
    public final SellerRepository sellerRepository;
    public final InboundRepository inboundRepository;
    public final AppointmentMapper appointmentMapper;

    @Override
    public AppointmentDomain save(AppointmentDomain appointmentDomain) {
        log.info("[save] Saving appointment. {}", appointmentDomain);
        final Seller seller = sellerRepository.getReferenceById(
                UUID.fromString(appointmentDomain.getSellerId()));

        final List<Inbound> inbounds = appointmentDomain.getInbounds().stream()
                .map(id -> inboundRepository.getReferenceById(UUID.fromString(id)))
                .toList();

        final Appointment appointment = appointmentMapper.toEntity(
                appointmentDomain,
                seller,
                inbounds);

        final Appointment appointmentResult = appointmentRepository.save(appointment);

        return appointmentMapper.toDomain(appointmentResult);
    }
}