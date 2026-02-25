package com.wms.appointment.entrypoint.controller.dtos;

import lombok.Builder;

import java.util.List;

@Builder
public record AppointmentResponseDTO(String id,
                                     String appointmentAt,
                                     SellerResponseDTO seller,
                                     List<ItemResponseDTO> items) {
}
