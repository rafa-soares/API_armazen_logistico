package com.wms.appointment.infra.gateway;

import com.wms.appointment.core.domain.InboundDomain;
import com.wms.appointment.core.domain.ItemDomain;
import com.wms.appointment.core.exceptions.InboundNotFoundException;
import com.wms.appointment.infra.mapper.InboundMapper;
import com.wms.appointment.infra.model.Inbound;
import com.wms.appointment.infra.model.Item;
import com.wms.appointment.infra.repository.InboundRepository;
import com.wms.appointment.infra.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InboundGatewayImpTest {
    @Mock
    private InboundRepository inboundRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private InboundMapper inboundMapper;

    @InjectMocks
    private InboundGatewayImp inboundGatewayImp;

    @Test
    public void shouldSaveInbound() {
        final ItemDomain itemDomainOutput = new ItemDomain(
                "626cd032-f7df-479e-8580-0d2e54255d19",
                1L,
                "Macbook",
                "PENDING");

        final InboundDomain inboundDomainOutput = new InboundDomain(
                "6945bc81-1348-4374-9597-3a9760e71bb6",
                "SCHEDULED",
                List.of(itemDomainOutput));

        final Item item = new Item(
                2L,
                "Iphone");

        final Inbound inbound = new Inbound(List.of(item));

        when(itemRepository.getReferenceById(UUID.fromString("626cd032-f7df-479e-8580-0d2e54255d19")))
                .thenReturn(item);
        when(inboundRepository.save(inbound)).thenReturn(inbound);
        when(inboundMapper.toEntity(List.of(item))).thenReturn(inbound);
        when(inboundMapper.toDomain(inbound)).thenReturn(inboundDomainOutput);

        final InboundDomain inboundDomainResult = inboundGatewayImp.save(inboundDomainOutput);

        assertThat(inboundDomainResult.getId()).isEqualTo("6945bc81-1348-4374-9597-3a9760e71bb6");
        assertThat(inboundDomainResult.getStatus()).isEqualTo("SCHEDULED");
        assertTrue(inboundDomainResult.getItems().stream()
                        .allMatch(itemDomain -> itemDomain.getId().equals("626cd032-f7df-479e-8580-0d2e54255d19")),
                "626cd032-f7df-479e-8580-0d2e54255d19");
        verify(inboundMapper, times(1)).toDomain(inbound);
    }

    @Test
    public void shouldFindAllById() {
        final ItemDomain itemDomain2 = new ItemDomain(
                "626cd032-f7df-479e-8580-0d2e54255d19",
                3L,
                "Macbook",
                "PENDING");

        final ItemDomain itemDomain1 = new ItemDomain(
                "626cd032-f7df-479e-8580-0d2e54255d19",
                2L,
                "Iphone",
                "PENDING");

        final InboundDomain inboundDomainOutput = new InboundDomain(
                "9ca592d0-5ffb-4347-ba21-2e59c85bb425",
                "SCHEDULED",
                List.of(itemDomain1, itemDomain2));

        final String id = "9ca592d0-5ffb-4347-ba21-2e59c85bb425";

        final List<UUID> uuidList= List.of(
                UUID.fromString(id));

        final Item item2 = new Item(
                3L,
                "Macbook");

        final Item item1 = new Item(
                2L,
                "Iphone");

        final Inbound inbound = new Inbound(
                List.of(item1, item2));

        when(inboundRepository.findAllById(uuidList)).thenReturn(List.of(inbound));
        when(inboundMapper.toDomain(inbound)).thenReturn(inboundDomainOutput);

        final List<InboundDomain> listInboundDomain = inboundGatewayImp.findAllById(List.of(id));

        assertTrue(listInboundDomain.stream()
                .allMatch(inboundDomain -> inboundDomain.getId().equals("9ca592d0-5ffb-4347-ba21-2e59c85bb425")),
                "9ca592d0-5ffb-4347-ba21-2e59c85bb425");
        assertTrue(listInboundDomain.stream()
                .allMatch(inboundDomain -> inboundDomain.getStatus().equals("SCHEDULED")),
                "SCHEDULED");
        verify(inboundMapper, times(1)).toDomain(inbound);
    }

    @Test
    public void shouldReturnInboundNotFoundException() {
        final String id = "9ca592d0-5ffb-4347-ba21-2e59c85bb425";

        final List<UUID> uuidList= List.of(
                UUID.fromString(id));

        final Inbound inbound = new Inbound(
                List.of());

        when(inboundRepository.findAllById(uuidList)).thenReturn(List.of());

        assertThrows(InboundNotFoundException.class, () -> inboundGatewayImp.findAllById(List.of(id)));

        verify(inboundMapper, times(0)).toDomain(inbound);
    }
}