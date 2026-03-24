package com.wms.inbound.infra.gateway;

import com.wms.inbound.core.domain.AppointmentDomain;
import com.wms.inbound.core.exceptions.AppointmentNotFoundException;
import com.wms.inbound.core.gateway.AppointmentGateway;
import com.wms.inbound.infra.mapper.AppointmentMapper;
import com.wms.inbound.infra.model.Appointment;
import com.wms.inbound.infra.model.Inbound;
import com.wms.inbound.infra.model.Seller;
import com.wms.inbound.infra.repository.AppointmentRepository;
import com.wms.inbound.infra.repository.InboundRepository;
import com.wms.inbound.infra.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
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

    @Override
    public AppointmentDomain findById(String appointmentId) {
        final UUID id = UUID.fromString(appointmentId);

        final Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));
        log.info("[findById] Returned appointment: {}", appointment);

        return appointmentMapper.toDomain(appointment);
    }
}