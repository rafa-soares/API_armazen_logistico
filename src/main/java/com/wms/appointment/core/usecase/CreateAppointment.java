package com.wms.appointment.core.usecase;

import com.wms.appointment.core.domain.AppointmentDomain;
import com.wms.appointment.core.domain.InboundDomain;
import com.wms.appointment.core.domain.SellerDomain;
import com.wms.appointment.core.gateway.InboundGateway;
import com.wms.appointment.core.gateway.SellerGateway;
import com.wms.appointment.entrypoint.controller.dtos.AppointmentRequestDTO;
import com.wms.appointment.entrypoint.controller.dtos.AppointmentResponseDTO;
import com.wms.appointment.infra.gateway.AppointmentGatewayImp;
import com.wms.appointment.infra.mapper.AppointmentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreateAppointment {
    private final AppointmentGatewayImp appointmentGateway;
    private final SellerGateway sellerGateway;
    private final InboundGateway inboundGateway;
    private final AppointmentMapper appointmentMapper;

    public AppointmentResponseDTO execute(final AppointmentRequestDTO appointmentRequest) {
        final SellerDomain sellerDomain = sellerGateway.findById(appointmentRequest.sellerId());

        final List<InboundDomain> inboundsDomain = inboundGateway.findAllById(appointmentRequest.inbounds());

        final AppointmentDomain appointmentDomain = appointmentMapper.toDomain(appointmentRequest, sellerDomain, inboundsDomain);

        return appointmentMapper.toResponse(appointmentGateway.save(appointmentDomain));
    }
}