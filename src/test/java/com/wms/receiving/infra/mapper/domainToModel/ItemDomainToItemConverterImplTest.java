package com.wms.receiving.infra.mapper.domainToModel;

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
class ItemDomainToItemConverterImplTest {
    @InjectMocks
    private ItemDomainToItemConverterImpl itemDomainConverter;

    private ItemDomain itemDomain;

    @BeforeEach
    public void setUp() {
        itemDomain = ItemDomain.builder()
                .description("Teste")
                .qty(5)
                .statusChecking(Status.OPEN)
                .build();
    }

    @Test
    public void shouldItemDomainToItem() {
        final Item item = itemDomainConverter.toItem(itemDomain);

        assertThat(item.getDescription()).isEqualTo("Teste");
        assertThat(item.getQty()).isEqualTo(5);
        assertThat(item.getStatusChecking()).isEqualTo(Status.OPEN);
    }
}