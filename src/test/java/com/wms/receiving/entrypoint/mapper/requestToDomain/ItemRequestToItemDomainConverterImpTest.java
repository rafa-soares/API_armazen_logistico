package com.wms.receiving.entrypoint.mapper.requestToDomain;

import com.wms.receiving.core.domain.ItemDomain;
import com.wms.receiving.entrypoint.controller.dtos.ItemRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ItemRequestToItemDomainConverterImpTest {
    @InjectMocks
    private ItemRequestToItemDomainConverterImp itemRequestConverter;

    private ItemRequestDTO itemRequest;

    @BeforeEach
    public void setUp() {
        itemRequest = ItemRequestDTO.builder()
                .description("Teste")
                .qty(1)
                .build();
    }

    @Test
    public void shouldConverterItemRequestToItemDomain() {
        final ItemDomain itemDomain = itemRequestConverter.toDomain(itemRequest);

        assertThat(itemDomain.getDescription()).isEqualTo("Teste");
        assertThat(itemDomain.getQty()).isEqualTo(1);
    }
}