package com.wms.appointment.infra.gateway;

import com.wms.appointment.core.domain.ItemDomain;
import com.wms.appointment.core.exceptions.ItemNotFoundException;
import com.wms.appointment.infra.mapper.ItemMapper;
import com.wms.appointment.infra.model.Item;
import com.wms.appointment.infra.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemGatewayImpTest {
    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemMapper itemMapper;

    @InjectMocks
    private ItemGatewayImp itemGatewayImp;

    @Test
    void shouldSaveItem() {
        final Item item = new Item(
                2L,
                "Iphone",
                "1234");

        final ItemDomain itemDomainInput = new ItemDomain(
                null,
                2L,
                "Iphone",
                "1234",
                null);

        final ItemDomain itemDomainOutput = new ItemDomain(
                "edfa5aa2-9d76-4909-83d5-8a79e90e34cf",
                2L,
                "Iphone",
                "1234",
                "PENDING");

        when(itemRepository.save(item)).thenReturn(item);
        when(itemMapper.toEntity(itemDomainInput)).thenReturn(item);
        when(itemMapper.toDomain(item)).thenReturn(itemDomainOutput);

        final ItemDomain itemDomain = itemGatewayImp.save(itemDomainInput);

        assertThat(itemDomain.getId()).isEqualTo("edfa5aa2-9d76-4909-83d5-8a79e90e34cf");
        assertThat(itemDomain.getQuantity()).isEqualTo(2L);
        assertThat(itemDomain.getDescription()).isEqualTo("Iphone");
        assertThat(itemDomain.getSku()).isEqualTo("1234");
        assertThat(itemDomain.getStatus()).isEqualTo("PENDING");
        verify(itemMapper, times(1)).toDomain(item);
    }

    @Test
    void shouldFindAllItemsById() {
        final List<String> ids = new ArrayList<>();
        ids.add("edfa5aa2-9d76-4909-83d5-8a79e90e34cf");
        ids.add("5d49bbcf-adde-4bd6-9c4b-35a684875142");

        final List<UUID> uuidList = ids.stream()
                .map(id ->  UUID.fromString(id))
                .toList();

        final Item item1 = new Item(
                2L,
                "Iphone",
                "1234");

        final Item item2 = new Item(
                1L,
                "Macbook",
                "5678");

        final List<Item> items = List.of(item1, item2);

        final ItemDomain itemDomainOutput1 = new ItemDomain(
                "edfa5aa2-9d76-4909-83d5-8a79e90e34cf",
                2L,
                "Iphone",
                "1234",
                "PENDING");

        final ItemDomain itemDomainOutput2 = new ItemDomain(
                "5d49bbcf-adde-4bd6-9c4b-35a684875142",
                1L,
                "Macbook",
                "5678",
                "PENDING");

        final List<ItemDomain> itemsDomain = List.of(itemDomainOutput1, itemDomainOutput2);

        when(itemRepository.findAllById(uuidList)).thenReturn(items);
        when(itemMapper.toDomain(items)).thenReturn(itemsDomain);

        final List<ItemDomain> itemsDomainResult = itemGatewayImp.findAllById(ids);

        assertEquals(itemsDomainResult, itemsDomain);
        verify(itemMapper, times(1)).toDomain(items);
    }

    @Test
    public void itemNotFoundException() {
        final List<String> ids = new ArrayList<>();
        ids.add("edfa5aa2-9d76-4909-83d5-8a79e90e34cf");
        ids.add("5d49bbcf-adde-4bd6-9c4b-35a684875142");

        final List<UUID> uuidList = ids.stream()
                .map(id ->  UUID.fromString(id))
                .toList();

        final Item item = mock(Item.class);

        when(itemRepository.findAllById(uuidList)).thenReturn(List.of(item));

        assertThrows(ItemNotFoundException.class, () ->
                itemGatewayImp.findAllById(ids));

        verifyNoInteractions(itemMapper);
    }
}