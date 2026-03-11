package com.wms.inbound.entrypoint.controller.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record InboundResponseDTO(String id,
                                 String status,
                                 List<ItemResponseDTO> items) {

    public InboundResponseDTO(String id, String status) {
        this(id, status, null);
    }
}
