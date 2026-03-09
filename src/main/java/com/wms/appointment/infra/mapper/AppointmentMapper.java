package com.wms.appointment.infra.mapper;

import com.wms.appointment.core.domain.AppointmentDomain;
import com.wms.appointment.core.domain.InboundDomain;
import com.wms.appointment.core.domain.ItemDomain;
import com.wms.appointment.core.domain.SellerDomain;
import com.wms.appointment.entrypoint.controller.dtos.AppointmentRequestDTO;
import com.wms.appointment.entrypoint.controller.dtos.AppointmentResponseDTO;
import com.wms.appointment.infra.model.Appointment;
import com.wms.appointment.infra.model.Inbound;
import com.wms.appointment.infra.model.Seller;

import java.util.List;

public interface AppointmentMapper {
    AppointmentDomain toDomain(AppointmentRequestDTO appointmentRequest);

    Appointment toEntity(AppointmentDomain appointmentDomain, Seller seller, List<Inbound> inbounds);

    AppointmentDomain toDomain(Appointment appointment);

    AppointmentResponseDTO toResponse(AppointmentDomain appointmentDomain);

//    AppointmentResponseDTO toResponse(AppointmentDomain appointmentDomain, SellerDomain sellerDomain, List<InboundDomain> inboundsDomain);
}
//AppointmentDomain toDomain(AppointmentRequestDTO appointmentRequest, SellerDomain sellerDomain, List<InboundDomain> inboundDomains);