package com.wms.receiving.infra.mapper.modelToDomain;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.core.domain.ItemDomain;
import com.wms.receiving.infra.model.Inbound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class InboundToInboundDomainConverterImpl implements InboundToInboundDomainConverter {
    private final ItemToItemDomainConverterImpl itemToItemDomainConverter;

    @Override
    public InboundDomain toDomain(final Inbound inbound) {
        if (inbound == null) {
            return null;
        }

        InboundDomain inboundDomain = new InboundDomain();
        inboundDomain.setId(inbound.getId());
        inboundDomain.setSeller(inbound.getSeller());
        inboundDomain.setCode(inbound.getCode());
        inboundDomain.setStatusReceiving(inbound.getStatusReceiving());
        inboundDomain.setStatusChecking(inbound.getStatusChecking());

        List<ItemDomain> items = inbound.getItems().stream()
                .map(item -> itemToItemDomainConverter.toDomain(item))
                .toList();

        inboundDomain.setItems(items);

        return inboundDomain;
    }

    @Override
    public List<InboundDomain> toDomain(final List<Inbound> inboundList) {
        return inboundList.stream()
                .map(inbound -> this.toDomain(inbound))
                .toList();
    }
}
