package com.wms.inbound.entrypoint.controller.dtos;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

public record SellerRequestDTO(
        @NotBlank(message = "O nome do seller não pode ser null ou empty.")
        String name,
        @NotBlank(message = "O cpf do seller não pode ser null ou empty.")
        @CNPJ(message = "CNPJ inválido")
        String cnpj) {
}
