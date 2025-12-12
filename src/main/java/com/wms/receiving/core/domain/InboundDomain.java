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

    public static Inbound toInbound(final InboundDomain inboundDomain) {
        final Inbound inbound = new Inbound();
        inbound.setId();
        inbound.setSeller(inboundDomain.getSeller());
        inbound.setCode();

        final List<Item> items = inboundDomain.getItems().stream()
                .map(item -> {
                    final Item itemEntity = ItemDomain.toItem(item);
                    itemEntity.setInbound(inbound);
                    return itemEntity;
                })
                .collect(Collectors.toList());

        inbound.setItems(items);

        return inbound;
    }

    public static InboundResponseDTO toResponse(final InboundDomain inboundDomain) {
        final InboundResponseDTO inboundResponseDTO = new InboundResponseDTO();
        inboundResponseDTO.setId(inboundDomain.getId());
        inboundResponseDTO.setStatusReceiving(inboundDomain.getStatusReceiving());
        inboundResponseDTO.setStatusChecking(inboundDomain.getStatusChecking());
        inboundResponseDTO.setCode(inboundDomain.getCode());

        final List<ItemResponseDTO> items = inboundDomain.getItems().stream()
                .map(item -> ItemDomain.toResponse(item))
                .collect(Collectors.toList());
        inboundResponseDTO.setItems(items);

        return inboundResponseDTO;
    }

    public static List<InboundResponseDTO> toResponse(final List<InboundDomain> inboundsDomain) {
        return inboundsDomain.stream()
                .map(inboundDomain -> InboundDomain.toResponse(inboundDomain))
                .collect(Collectors.toList());
    }
}
