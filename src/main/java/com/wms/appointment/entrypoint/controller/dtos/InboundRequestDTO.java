package com.wms.appointment.entrypoint.controller.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.List;

@Builder
public record InboundRequestDTO(
        List<@NotBlank(message = "O id do item não pode ser null ou empty.") String> items) {
}
