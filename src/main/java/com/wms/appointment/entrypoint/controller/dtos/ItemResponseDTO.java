package com.wms.appointment.entrypoint.controller.dtos;

import lombok.Builder;

@Builder
public record ItemResponseDTO(String id,
                              Long quantity,
                              String description,
                              String sku,
                              String status) {
}
