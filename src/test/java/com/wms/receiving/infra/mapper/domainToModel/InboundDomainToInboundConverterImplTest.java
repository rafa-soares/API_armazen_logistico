package com.wms.receiving.infra.mapper.domainToModel;

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
class InboundDomainToInboundConverterImplTest {
    @InjectMocks
    private InboundDomainToInboundConverterImpl inboundConverter;

    @Mock
    private ItemDomainToItemConverterImpl itemConverter;

    private InboundDomain inboundDomain;

    private ItemDomain itemDomain;

    private final Item item = new Item();

    @BeforeEach
    void setUp() {
        item.setDescription("Teste");
        item.setQty(1);
        item.setStatusChecking(Status.OPEN);

        itemDomain = ItemDomain.builder()
                .description("Teste")
                .qty(1)
                .statusChecking(Status.OPEN)
                .build();

        inboundDomain = InboundDomain.builder()
                .code("123")
                .seller("Teste")
                .statusChecking(Status.OPEN)
                .items(List.of(itemDomain))
                .build();
    }

    @Test
    void shouldConvertInboundDomainToInbound() {
        when(itemConverter.toItem(itemDomain)).thenReturn(item);

        final Inbound inbound = inboundConverter.toInbound(inboundDomain);

        assertThat(inbound.getSeller()).isEqualTo("Teste");
        assertThat(inbound.getStatusChecking()).isEqualTo(Status.OPEN);
    }
}