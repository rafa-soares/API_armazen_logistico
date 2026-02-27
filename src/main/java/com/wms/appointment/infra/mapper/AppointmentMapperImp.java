package com.wms.appointment.infra.mapper;

import com.wms.appointment.core.domain.AppointmentDomain;
import com.wms.appointment.core.domain.InboundDomain;
import com.wms.appointment.core.domain.SellerDomain;
import com.wms.appointment.entrypoint.controller.dtos.AppointmentRequestDTO;
import com.wms.appointment.entrypoint.controller.dtos.AppointmentResponseDTO;
import com.wms.appointment.entrypoint.controller.dtos.InboundResponseDTO;
import com.wms.appointment.entrypoint.controller.dtos.SellerResponseDTO;
import com.wms.appointment.infra.model.Appointment;
import com.wms.appointment.infra.model.Inbound;
import com.wms.appointment.infra.model.Seller;
import com.wms.appointment.infra.repository.InboundRepository;
import com.wms.appointment.infra.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class AppointmentMapperImp implements AppointmentMapper {
    public final SellerRepository sellerRepository;
    public final InboundRepository inboundRepository;
    public final SellerMapper sellerMapper;
    public final InboundMapper inboundMapper;

    @Override
    public AppointmentDomain toDomain(AppointmentRequestDTO appointmentRequest, SellerDomain sellerDomain, List<InboundDomain> inboundDomains) {
        return new AppointmentDomain(null, appointmentRequest.appointmentAt(), sellerDomain, inboundDomains);
    }

    @Override
    public Appointment toEntity(AppointmentDomain appointmentDomain) {
        final LocalDateTime appointmentAt = LocalDateTime.parse(appointmentDomain.getAppointmentAt());

        final Seller seller = sellerRepository
                .getReferenceById(UUID.fromString(appointmentDomain.getSeller().getId()));

        final List<Inbound> inbounds = appointmentDomain.getInbounds().stream()
                .map(inboundDomain -> inboundRepository.getReferenceById(UUID.fromString(inboundDomain.getId())))
                .toList();

        return new Appointment(appointmentAt, seller, inbounds);
    }

    @Override
    public AppointmentDomain toDomain(Appointment appointment) {
        final String appointmentAt = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(appointment.getAppointmentAt());

        final SellerDomain sellerDomain = sellerMapper.toDomain(appointment.getSeller());

        final List<InboundDomain> inbounds = appointment.getInbound().stream()
                .map(inbound -> inboundMapper.toDomain(inbound))
                .toList();

        return new AppointmentDomain(appointment.getId().toString(), appointmentAt, sellerDomain, inbounds);
    }

    @Override
    public AppointmentResponseDTO toResponse(AppointmentDomain appointmentDomain) {
        final SellerResponseDTO sellerResponse = sellerMapper.toResponse(appointmentDomain.getSeller());

        final List<InboundResponseDTO> inboundsResponse = appointmentDomain.getInbounds().stream()
                .map(inboundDomain -> inboundMapper.toResponse(inboundDomain))
                .toList();

        return new AppointmentResponseDTO(appointmentDomain.getId(), appointmentDomain.getAppointmentAt(), sellerResponse, inboundsResponse);
    }
}
