package com.wms.receiving.infra.gateway;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.core.domain.ItemDomain;
import com.wms.receiving.infra.model.Inbound;
import com.wms.receiving.infra.model.Item;
import com.wms.receiving.infra.model.Status;
import com.wms.receiving.infra.repository.InboundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InboundGatewayImpTest {
    @Mock
    private InboundRepository inboundRepository;

    @InjectMocks
    private InboundGatewayImp inboundGatewayImp;

    private Inbound inbound;

    private Item item;

    private InboundDomain inboundDomain;

    private ItemDomain itemDomain;

    @BeforeEach
    void setUp() {
        item = new Item();
        item.setDescription("Carro");
        item.setQty(1);
        item.setStatus(Status.OPEN);

        inbound = new Inbound();
        inbound.setSeller("Nathália");
        inbound.setStatus(Status.OPEN);
        inbound.setCode("IS_73_09_76_39");
        inbound.setItems(List.of(item));

        itemDomain = ItemDomain.builder()
                .description("Carro")
                .qty(1)
                .status(Status.OPEN)
                .build();

        inboundDomain = InboundDomain.builder()
                .seller("Nathália")
                .status(Status.OPEN)
                .items(List.of(ItemDomain.builder()
                        .description("Carro")
                        .qty(1)
                        .status(Status.OPEN)
                        .build()))
                .build();
    }

    @Test
    public void shouldCreateInbound() {
        when(inboundRepository.save(any(Inbound.class))).thenReturn(inbound);

        final InboundDomain inboundResponse = inboundGatewayImp.saveInbound(inboundDomain);

        assertEquals(inbound.getStatus(), inboundResponse.getStatus());
        assertEquals(inbound.getCode(), inboundResponse.getCode());
        verify(inboundRepository, times(1)).save(any(Inbound.class));
    }

    @Test
    public void shouldFindAllInbounds() {
        when(inboundRepository.findAll()).thenReturn(List.of(inbound));

        final List<InboundDomain> inboundResponse = inboundGatewayImp.findAllInbounds();

        assertTrue(inboundResponse.stream()
                        .allMatch(inboundDomain -> inboundDomain.getSeller().equals("Nathália")),
                "Nathália");
        assertTrue(inboundResponse.stream()
                        .allMatch(inboundDomain -> inboundDomain.getStatus().equals(Status.OPEN)),
                "OPEN");
        assertTrue(inboundResponse.stream()
                        .allMatch(inboundDomain -> inboundDomain.getItems().equals(List.of(itemDomain))),
                List.of(itemDomain).toString());
        verify(inboundRepository, times(1)).findAll();
    }

    @Test
    public void shouldFindFirstInbound() {
        when(inboundRepository.findAll()).thenReturn(List.of(inbound));

        InboundDomain inboundResponse = inboundGatewayImp.findFirstInbound();

        assertThat(inboundResponse.getSeller()).isEqualTo("Nathália");
        assertThat(inboundResponse.getStatus()).isEqualTo(Status.OPEN);
        assertTrue(inboundResponse.getItems().stream()
                        .allMatch(itemDomain -> itemDomain.getDescription().equals("Carro")),
                "Carro");
        assertTrue(inboundResponse.getItems().stream()
                        .allMatch(itemDomain -> itemDomain.getQty().equals(1)),
                "1");
        verify(inboundRepository, times(1)).findAll();
    }

    @Test
    public void shouldFindByCode() {
        when(inboundRepository.findInboundByCode("IS_73_09_76_39")).thenReturn(inbound);

        InboundDomain inboundResponse = inboundGatewayImp.findInboundByCode("IS_73_09_76_39");

        assertThat(inboundResponse.getSeller()).isEqualTo("Nathália");
        assertThat(inboundResponse.getCode()).isEqualTo("IS_73_09_76_39");
        assertThat(inboundResponse.getStatus()).isEqualTo(Status.OPEN);
        verify(inboundRepository, times(1)).findInboundByCode("IS_73_09_76_39");
    }
}