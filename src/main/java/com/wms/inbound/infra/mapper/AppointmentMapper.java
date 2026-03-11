package com.wms.inbound.infra.mapper;

import com.wms.inbound.core.domain.AppointmentDomain;
import com.wms.inbound.entrypoint.controller.dtos.AppointmentRequestDTO;
import com.wms.inbound.entrypoint.controller.dtos.AppointmentResponseDTO;
import com.wms.inbound.infra.model.Appointment;
import com.wms.inbound.infra.model.Inbound;
import com.wms.inbound.infra.model.Seller;

import java.util.List;

public interface AppointmentMapper {
    AppointmentDomain toDomain(AppointmentRequestDTO appointmentRequest);

    Appointment toEntity(AppointmentDomain appointmentDomain, Seller seller, List<Inbound> inbounds);

    AppointmentDomain toDomain(Appointment appointment);

    AppointmentResponseDTO toResponse(AppointmentDomain appointmentDomain);

//    AppointmentResponseDTO toResponse(AppointmentDomain appointmentDomain, SellerDomain sellerDomain, List<InboundDomain> inboundsDomain);
}
//AppointmentDomain toDomain(AppointmentRequestDTO appointmentRequest, SellerDomain sellerDomain, List<InboundDomain> inboundDomains);