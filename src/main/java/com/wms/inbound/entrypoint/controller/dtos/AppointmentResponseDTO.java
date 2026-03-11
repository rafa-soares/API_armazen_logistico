package com.wms.inbound.entrypoint.controller.dtos;

import lombok.Builder;

import java.util.List;

@Builder
public record AppointmentResponseDTO(String id,
                                     String appointmentAt,
                                     SellerResponseDTO seller,
                                     List<InboundResponseDTO> inbounds) {
}
