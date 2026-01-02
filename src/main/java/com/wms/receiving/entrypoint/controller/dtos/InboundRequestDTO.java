package com.wms.receiving.entrypoint.controller.dtos;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.core.domain.ItemDomain;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InboundRequestDTO {
    @NotBlank(message = "O atributo seller não pode ser null ou empty.")
    private String seller;

    @NotEmpty(message = "Os items não podem ser null ou empty.")
    private List<ItemRequestDTO> items;
}