package com.wms.receiving.entrypoint.dtos;

import com.wms.receiving.core.domain.ItemDomain;
import com.wms.receiving.infra.model.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class ItemRequestDTOTest {

    @Test
    public void shouldConvertItemRequestToItemDomain() {
        ItemRequestDTO itemRequest = ItemRequestDTO.builder()
                .description("Teste")
                .qty(1)
                .build();

        final ItemDomain itemDomain = ItemRequestDTO.toDomain(itemRequest);

        assertThat(itemDomain.getDescription()).isEqualTo("Teste");
        assertThat(itemDomain.getQty()).isEqualTo(1);
    }
}