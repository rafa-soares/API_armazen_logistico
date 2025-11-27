package com.wms.receiving.entrypoint.dtos;

import com.wms.receiving.core.domain.ItemDomain;
import com.wms.receiving.infra.model.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDTO {
    @NotBlank(message = "O description qty não pode ser null ou empty.")
    private String description;

    @NotBlank(message = "O sku qty não pode ser null ou empty.")
    private String sku;

    @NotBlank(message = "O atributo qty não pode ser null ou empty.")
    private Integer qty;

    public static ItemDomain toDomain(final ItemRequestDTO itemRequestDTO) {
        final ItemDomain itemDomain = new ItemDomain();
        itemDomain.setDescription(itemRequestDTO.getDescription());
        itemDomain.setSku(itemRequestDTO.getSku());
        itemDomain.setQty(itemRequestDTO.getQty());
        return itemDomain;
    }
}
