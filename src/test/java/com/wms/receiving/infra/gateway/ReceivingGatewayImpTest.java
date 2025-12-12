package com.wms.receiving.infra.gateway;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.core.domain.ItemDomain;
import com.wms.receiving.entrypoint.exceptions.InboundNotFoundException;
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
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceivingGatewayImpTest {
    @Mock
    private InboundRepository inboundRepository;

    @InjectMocks
    private ReceivingGatewayImp receivingGatewayImp;

    private Inbound inbound;

    private Item item;

    private ItemDomain itemDomain;

    @BeforeEach
    void setUp() {
        item = new Item();
        item.setDescription("Carro");
        item.setQty(1);
        item.setStatusChecking(Status.OPEN);

        inbound = new Inbound();
        inbound.setId(12345678L);
        inbound.setSeller("Nathália");
        inbound.setStatusReceiving(Status.PENDING);
        inbound.setStatusChecking(Status.OPEN);
        inbound.setCode("IS_73_09_76_39");
        inbound.setItems(List.of(item));

        itemDomain = ItemDomain.builder()
                .description("Carro")
                .qty(1)
                .statusChecking(Status.OPEN)
                .build();
    }

    @Test
    public void shouldFindAllInbounds() {
        when(inboundRepository.findAll()).thenReturn(List.of(inbound));

        final List<InboundDomain> inbounds = receivingGatewayImp.findAllInbounds();

        assertTrue(inbounds.stream()
                        .allMatch(inboundDomain -> inboundDomain.getSeller().equals("Nathália")),
                "Nathália");
        assertTrue(inbounds.stream()
                        .allMatch(inboundDomain -> inboundDomain.getStatusChecking().equals(Status.OPEN)),
                "OPEN");
        assertTrue(inbounds.stream()
                        .allMatch(inboundDomain -> inboundDomain.getItems().equals(List.of(itemDomain))),
                List.of(itemDomain).toString());
        verify(inboundRepository, times(1)).findAll();
    }

    @Test
    public void shouldFindFirstInbound() {
        when(inboundRepository.findAll()).thenReturn(List.of(inbound));

        InboundDomain inboundDomain = receivingGatewayImp.findFirstInbound();

        assertThat(inboundDomain.getSeller()).isEqualTo("Nathália");
        assertThat(inboundDomain.getStatusChecking()).isEqualTo(Status.OPEN);
        assertTrue(inboundDomain.getItems().stream()
                        .allMatch(itemDomain -> itemDomain.getDescription().equals("Carro")),
                "Carro");
        assertTrue(inboundDomain.getItems().stream()
                        .allMatch(itemDomain -> itemDomain.getQty().equals(1)),
                "1");
        verify(inboundRepository, times(1)).findAll();
    }

    @Test
    public void shouldFindByCode() {
        when(inboundRepository.findInboundByCode("IS_73_09_76_39")).thenReturn(inbound);

        InboundDomain inboundDomain = receivingGatewayImp.findInboundByCode("IS_73_09_76_39");

        assertThat(inboundDomain.getSeller()).isEqualTo("Nathália");
        assertThat(inboundDomain.getCode()).isEqualTo("IS_73_09_76_39");
        assertThat(inboundDomain.getStatusChecking()).isEqualTo(Status.OPEN);
        verify(inboundRepository, times(1)).findInboundByCode("IS_73_09_76_39");
    }

    @Test
    public void shouldUpdateStatusInbound() {
        when(inboundRepository.findById(12345678L)).thenReturn(Optional.of(inbound));
        when(inboundRepository.save(inbound)).thenReturn(inbound);

        final InboundDomain inboundDomain = receivingGatewayImp.updateStatusInbound(12345678L);

        assertThat(inboundDomain.getStatusReceiving()).isEqualTo(Status.RECEIVED);
        verify(inboundRepository, times(1)).findById(12345678L);
        verify(inboundRepository, times(1)).save(inbound);
    }

    @Test
    public void shouldReturnInboundNotFoundException() {
        when(inboundRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(InboundNotFoundException.class, () -> receivingGatewayImp.updateStatusInbound(1L));

        verify(inboundRepository, times(1)).findById(1L);
        verify(inboundRepository, times(0)).save(inbound);
    }
}