package com.wms.appointment.entrypoint.controller.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ItemRequestDTO(
        @NotNull(message = "A quantidade não pode ser null.")
        Long quantity,
        @NotBlank(message = "A descrição do seller não pode ser null ou empty.")
        String description
//        @NotBlank(message = "O sku do seller não pode ser null ou empty.")
//        String sku)
) {
}
