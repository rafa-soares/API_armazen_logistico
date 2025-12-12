package com.wms.receiving.entrypoint.controller.dtos;

import com.wms.receiving.infra.model.Status;
import lombok.Data;

import java.util.List;

@Data
public class InboundResponseDTO {
    private Long id;

    private Status statusReceiving;

    private Status statusChecking;

    private String code;

    private List<ItemResponseDTO> items;
}