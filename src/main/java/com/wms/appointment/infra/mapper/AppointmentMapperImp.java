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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Component
public class AppointmentMapperImp implements AppointmentMapper {
    public final SellerMapper sellerMapper;
    public final InboundMapper inboundMapper;

    @Override
    public AppointmentDomain toDomain(AppointmentRequestDTO appointmentRequest) {
        return new AppointmentDomain(null, appointmentRequest.appointmentAt(), appointmentRequest.sellerId(), null, appointmentRequest.inbounds(), null);
    }

    @Override
    public Appointment toEntity(AppointmentDomain appointmentDomain, Seller seller, List<Inbound> inbounds) {
        final LocalDateTime appointmentAt = LocalDateTime.parse(appointmentDomain.getAppointmentAt());

        return new Appointment(appointmentAt, seller, inbounds);
    }

    @Override
    public AppointmentDomain toDomain(Appointment appointment) {
        final String appointmentAt = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(appointment.getAppointmentAt());

        final SellerDomain sellerDomain = sellerMapper.toDomain(appointment.getSeller());

        final List<InboundDomain> inbounds = appointment.getInbounds().stream()
                .map(inbound -> inboundMapper.toDomain(inbound))
                .toList();

        return new AppointmentDomain(appointment.getId().toString(), appointmentAt, null, sellerDomain, null, inbounds);
    }

    @Override
    public AppointmentResponseDTO toResponse(AppointmentDomain appointmentDomain) {
        final SellerResponseDTO sellerResponse = sellerMapper.toResponse(appointmentDomain.getSeller());

        final List<InboundResponseDTO> inboundResponse = appointmentDomain.getInboundsDomain().stream()
                .map(inboundDomain -> inboundMapper.toResponse(inboundDomain))
                .toList();

        return AppointmentResponseDTO.builder()
                .id(appointmentDomain.getId())
                .appointmentAt(appointmentDomain.getAppointmentAt())
                .seller(sellerResponse)
                .inbounds(inboundResponse)
                .build();
    }

//    @Override
//    public AppointmentResponseDTO toResponse(AppointmentDomain appointmentDomain, SellerDomain sellerDomain, List<InboundDomain> inboundsDomain) {
//        final SellerResponseDTO sellerResponse = sellerMapper.toResponse(sellerDomain);
//
//        final List<InboundResponseDTO> inboundsResponse = inboundsDomain.stream()
//                .map(inboundDomain -> inboundMapper.toResponse(inboundDomain))
//                .toList();
//
//        return new AppointmentResponseDTO(appointmentDomain.getId(), appointmentDomain.getAppointmentAt(), sellerResponse, inboundsResponse);
//    }
}
