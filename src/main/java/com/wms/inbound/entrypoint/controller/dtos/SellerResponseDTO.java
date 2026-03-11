package com.wms.inbound.entrypoint.controller.dtos;

import lombok.Builder;

@Builder
public record SellerResponseDTO(String id,
                                String name,
                                String cnpj) {
}
