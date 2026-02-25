package com.wms.appointment.entrypoint.controller.dtos;

import lombok.Builder;

import java.util.List;

@Builder
public record InboundResponseDTO(String id,
                                 String status,
                                 List<ItemResponseDTO> items) {
}
