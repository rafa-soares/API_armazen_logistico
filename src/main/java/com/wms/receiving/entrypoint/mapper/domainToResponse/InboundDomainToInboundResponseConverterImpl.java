package com.wms.receiving.entrypoint.mapper.domainToResponse;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.entrypoint.controller.dtos.InboundResponseDTO;
import com.wms.receiving.entrypoint.controller.dtos.ItemResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class InboundDomainToInboundResponseConverterImpl implements InboundDomainToInboundResponseConverter {
    private final ItemDomainToItemResponseConverterImpl itemConverter;

    @Override
    public InboundResponseDTO toResponse(InboundDomain inboundDomain) {
        final InboundResponseDTO inboundResponseDTO = new InboundResponseDTO();
        inboundResponseDTO.setId(inboundDomain.getId());
        inboundResponseDTO.setStatusReceiving(inboundDomain.getStatusReceiving());
        inboundResponseDTO.setStatusChecking(inboundDomain.getStatusChecking());
        inboundResponseDTO.setCode(inboundDomain.getCode());

        final List<ItemResponseDTO> items = inboundDomain.getItems().stream()
                .map(item -> itemConverter.toResponse(item))
                .collect(Collectors.toList());
        inboundResponseDTO.setItems(items);

        return inboundResponseDTO;
    }

    @Override
    public List<InboundResponseDTO> toResponse(List<InboundDomain> inboundsDomain) {
        return inboundsDomain.stream()
                .map(inboundDomain -> this.toResponse(inboundDomain))
                .collect(Collectors.toList());
    }
}
