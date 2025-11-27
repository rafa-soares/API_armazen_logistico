package com.wms.receiving.entrypoint.dtos;

import com.wms.receiving.infra.model.Status;
import lombok.Data;

@Data
public class ItemResponseDTO {
    private Long id;

    private String description;

    private String sku;

    private Integer qty;

    private Status status;
}