package com.wms.receiving.infra.mapper.modelToDomain;

import com.wms.receiving.core.domain.ItemDomain;
import com.wms.receiving.infra.model.Item;
import com.wms.receiving.infra.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ItemToItemDomainConverterImplTest {
    @InjectMocks
    ItemToItemDomainConverterImpl itemConverter;

    private Item item = new Item();

    @BeforeEach
    public void setup() {
        item.setId(1L);
        item.setDescription("Teste");
        item.setSku("SKU123");
        item.setQty(10);
        item.setStatusChecking(Status.PENDING);
    }

    @Test
    void shouldConverterItemToItemDomain() {
        final ItemDomain itemDomain = itemConverter.toDomain(item);

        assertThat(itemDomain.getQty()).isEqualTo(10L);
        assertThat(itemDomain.getDescription()).isEqualTo("Teste");
        assertThat(itemDomain.getStatusChecking()).isEqualTo(Status.PENDING);
    }
}