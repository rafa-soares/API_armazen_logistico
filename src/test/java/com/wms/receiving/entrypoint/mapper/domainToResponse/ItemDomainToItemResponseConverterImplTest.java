package com.wms.receiving.entrypoint.mapper.domainToResponse;

import com.wms.receiving.core.domain.ItemDomain;
import com.wms.receiving.entrypoint.controller.dtos.ItemResponseDTO;
import com.wms.receiving.infra.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ItemDomainToItemResponseConverterImplTest {
    @InjectMocks
    private ItemDomainToItemResponseConverterImpl itemDomainConverter;

    private ItemDomain itemDomain;

    @BeforeEach
    public void setUp() {
        itemDomain = ItemDomain.builder()
                .description("Teste")
                .qty(1)
                .statusChecking(Status.OPEN)
                .build();
    }

    @Test
    public void shouldConverterItemDomainToItemResponse() {

        final ItemResponseDTO itemResponse = itemDomainConverter.toResponse(itemDomain);

        assertThat(itemResponse.getDescription()).isEqualTo("Teste");
        assertThat(itemResponse.getQty()).isEqualTo(1);
        assertThat(itemResponse.getStatusChecking()).isEqualTo(Status.OPEN);
    }
}