package com.wms.receiving.infra.mapper.domainToModel;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.infra.model.Inbound;
import com.wms.receiving.infra.model.Item;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class InboundDomainToInboundConverterImpl implements InboundDomainToInboundConverter {
    private final ItemDomainToItemConverter itemConverter;

    @Override
    public Inbound toInbound(InboundDomain inboundDomain) {
        final Inbound inbound = new Inbound();
        inbound.setId();
        inbound.setSeller(inboundDomain.getSeller());
        inbound.setCode();

        final List<Item> items = inboundDomain.getItems().stream()
                .map(item -> {
                    final Item itemEntity = itemConverter.toItem(item);
                    itemEntity.setInbound(inbound);
                    return itemEntity;
                })
                .collect(Collectors.toList());

        inbound.setItems(items);

        return inbound;
    }
}
