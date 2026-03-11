package com.wms.inbound.entrypoint.controller.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record AppointmentRequestDTO(
        @NotBlank(message = "A data de agendamento não pode ser null ou empty.")
        String appointmentAt,
        @NotBlank(message = "O id do seller não pode ser null ou empty.")
        String sellerId,
        @NotEmpty(message = "A lista de inbounds não pode estar vazia.")
        List<@NotBlank(message = "O id do item não pode ser null ou empty.") String> inbounds) {
}