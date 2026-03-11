package com.wms.inbound.core.usecase;

import com.wms.inbound.core.domain.AppointmentDomain;
import com.wms.inbound.core.domain.InboundDomain;
import com.wms.inbound.core.domain.SellerDomain;
import com.wms.inbound.core.gateway.InboundGateway;
import com.wms.inbound.core.gateway.SellerGateway;
import com.wms.inbound.entrypoint.controller.dtos.AppointmentRequestDTO;
import com.wms.inbound.entrypoint.controller.dtos.AppointmentResponseDTO;
import com.wms.inbound.infra.gateway.AppointmentGatewayImp;
import com.wms.inbound.infra.mapper.AppointmentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreateAppointment {
    private final AppointmentGatewayImp appointmentGateway;
    private final SellerGateway sellerGateway;
    private final InboundGateway inboundGateway;
    private final AppointmentMapper appointmentMapper;

    @Transactional
    public AppointmentResponseDTO execute(final AppointmentRequestDTO appointmentRequest) {
        final SellerDomain sellerDomain = sellerGateway.findById(appointmentRequest.sellerId());

        final List<InboundDomain> inboundsDomain = inboundGateway.findAllById(appointmentRequest.inbounds());

        final AppointmentDomain appointmentDomain = appointmentMapper.toDomain(appointmentRequest);
        appointmentDomain.setSeller(sellerDomain);
        appointmentDomain.setInboundsDomain(inboundsDomain);

        final AppointmentDomain appointmentResult = appointmentGateway.save(appointmentDomain);
        log.info("[execute] Appointment save. {}", appointmentResult);

        return appointmentMapper.toResponse(appointmentResult);
    }
}