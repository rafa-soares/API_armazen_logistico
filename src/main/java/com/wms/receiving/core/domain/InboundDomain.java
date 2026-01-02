package com.wms.receiving.core.domain;

import com.wms.receiving.entrypoint.controller.dtos.InboundResponseDTO;
import com.wms.receiving.entrypoint.controller.dtos.ItemResponseDTO;
import com.wms.receiving.infra.model.Inbound;
import com.wms.receiving.infra.model.Item;
import com.wms.receiving.infra.model.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InboundDomain {
    private Long id;

    @NotBlank(message = "O atributo seller não pode ser null ou empty.")
    private String seller;

    private String code;

    private Status statusReceiving;

    private Status statusChecking;

    private List<ItemDomain> items;
}
