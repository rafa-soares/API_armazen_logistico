package com.wms.receiving.entrypoint.dtos;

import com.wms.receiving.infra.model.Status;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class InboundResponseDTO {
    private Long id;

    private Status status;

    private String code;

    private List<ItemResponseDTO> items;
}