package com.wms.receiving.entrypoint.mapper.requestToDomain;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.core.domain.ItemDomain;
import com.wms.receiving.entrypoint.controller.dtos.InboundRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class InboundRequestToInboundDomainConverterImpl implements InboundRequestToInboundDomainConverter {
    private final ItemRequestToItemDomainConverter itemConverter;

    @Override
    public InboundDomain toDomain(InboundRequestDTO inboundRequestDTO) {
        final InboundDomain inboundDomain = new InboundDomain();
        inboundDomain.setSeller(inboundRequestDTO.getSeller());

        final List<ItemDomain> items = inboundRequestDTO.getItems().stream()
                .map(item -> itemConverter.toDomain(item))
                .collect(Collectors.toList());
        inboundDomain.setItems(items);

        return inboundDomain;
    }
}
