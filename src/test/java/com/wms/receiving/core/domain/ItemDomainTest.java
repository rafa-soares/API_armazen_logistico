package com.wms.receiving.core.domain;

import com.wms.receiving.entrypoint.controller.dtos.ItemResponseDTO;
import com.wms.receiving.infra.model.Item;
import com.wms.receiving.infra.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class ItemDomainTest {
    private ItemDomain itemDomain;

    @BeforeEach
    void setUp() {
        itemDomain = ItemDomain.builder()
                .description("Teste")
                .qty(1)
                .statusChecking(Status.OPEN)
                .build();
    }

    @Test
    public void shouldConvertItemDomainToItem() {
        final Item item = ItemDomain.toItem(itemDomain);

        assertThat(item.getDescription()).isEqualTo("Teste");
        assertThat(item.getQty()).isEqualTo(1);
        assertThat(item.getStatusChecking()).isEqualTo(Status.OPEN);
    }

    @Test
    public void shouldConvertItemDomainToItemResponse() {
        final ItemResponseDTO itemResponseDTO = ItemDomain.toResponse(itemDomain);

        assertThat(itemResponseDTO.getDescription()).isEqualTo("Teste");
        assertThat(itemResponseDTO.getQty()).isEqualTo(1);
        assertThat(itemResponseDTO.getStatusChecking()).isEqualTo(Status.OPEN);
    }
}