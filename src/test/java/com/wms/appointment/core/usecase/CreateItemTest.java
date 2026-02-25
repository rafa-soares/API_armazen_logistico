package com.wms.appointment.core.usecase;

import com.wms.appointment.core.domain.ItemDomain;
import com.wms.appointment.core.gateway.ItemGateway;
import com.wms.appointment.entrypoint.controller.dtos.ItemRequestDTO;
import com.wms.appointment.entrypoint.controller.dtos.ItemResponseDTO;
import com.wms.appointment.infra.mapper.ItemMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateItemTest {
    @Mock
    private ItemGateway itemGateway;

    @Mock
    private ItemMapper itemMapper;

    @InjectMocks
    private CreateItem createItem;

    @Test
    void shouldCreateItem() {
        final ItemResponseDTO itemResponse = ItemResponseDTO.builder()
                .id("edfa5aa2-9d76-4909-83d5-8a79e90e34cf")
                .quantity(2L)
                .description("Patins")
                .sku("1234")
                .status("PENDING")
                .build();

        final ItemRequestDTO itemRequest = new ItemRequestDTO(
                2L,
                "Patins",
                "1234");

        final ItemDomain itemDomainOutput = new ItemDomain(
                "edfa5aa2-9d76-4909-83d5-8a79e90e34cf",
                2L,
                "Patins",
                "1234",
                "PENDING");


        final ItemDomain itemDomainInput = new ItemDomain(
                null,
                2L,
                "Patins",
                "1234",
                null);

        when(itemGateway.save(itemDomainInput)).thenReturn(itemDomainOutput);
        when(itemMapper.toDomain(itemRequest)).thenReturn(itemDomainInput);
        when(itemMapper.toResponse(itemDomainOutput)).thenReturn(itemResponse);

        final ItemResponseDTO itemResponseResult = createItem.execute(itemRequest);

        assertThat(itemResponseResult.id()).isEqualTo("edfa5aa2-9d76-4909-83d5-8a79e90e34cf");
        assertThat(itemResponseResult.quantity()).isEqualTo(2L);
        assertThat(itemResponseResult.description()).isEqualTo("Patins");
        assertThat(itemResponseResult.sku()).isEqualTo("1234");
        assertThat(itemResponseResult.status()).isEqualTo("PENDING");
        verify(itemMapper, times(1)).toResponse(itemDomainOutput);
    }
}