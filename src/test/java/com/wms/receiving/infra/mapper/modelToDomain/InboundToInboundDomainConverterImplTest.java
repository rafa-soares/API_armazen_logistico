package com.wms.receiving.infra.mapper.modelToDomain;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.core.domain.ItemDomain;
import com.wms.receiving.infra.model.Inbound;
import com.wms.receiving.infra.model.Item;
import com.wms.receiving.infra.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InboundToInboundDomainConverterImplTest {
    @InjectMocks
    InboundToInboundDomainConverterImpl inboundConverter;

    @Mock
    private ItemToItemDomainConverterImpl itemConverter;

    private ItemDomain itemDomain;

    private final Item item = new Item();

    private final Inbound inbound = new Inbound();

    @BeforeEach
    void setUp() {
        item.setDescription("Teste");
        item.setQty(1);
        item.setStatusChecking(Status.OPEN);

        inbound.setCode("123");
        inbound.setSeller("Teste");
        inbound.setStatusChecking(Status.OPEN);
        inbound.setItems(List.of(item));

        itemDomain = ItemDomain.builder()
                .description("Teste")
                .qty(1)
                .statusChecking(Status.OPEN)
                .build();
    }

    @Test
    void shouldConvertInboundToInboundDomain() {
        when(itemConverter.toDomain(item)).thenReturn(itemDomain);

        final InboundDomain inboundDomain = inboundConverter.toDomain(inbound);

        assertThat(inboundDomain.getSeller()).isEqualTo("Teste");
        assertThat(inboundDomain.getStatusChecking()).isEqualTo(Status.OPEN);
    }
}