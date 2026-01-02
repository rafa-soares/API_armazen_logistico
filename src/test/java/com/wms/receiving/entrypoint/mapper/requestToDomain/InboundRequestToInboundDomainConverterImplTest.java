package com.wms.receiving.entrypoint.mapper.requestToDomain;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.core.domain.ItemDomain;
import com.wms.receiving.entrypoint.controller.dtos.InboundRequestDTO;
import com.wms.receiving.entrypoint.controller.dtos.ItemRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InboundRequestToInboundDomainConverterImplTest {
    @InjectMocks
    private InboundRequestToInboundDomainConverterImpl inboundRequestConverter;

    @Mock
    private ItemRequestToItemDomainConverter itemConverter;

    private ItemDomain itemDomain;

    private InboundRequestDTO inboundRequest;

    private ItemRequestDTO itemRequest;

    @BeforeEach
    void setUp() {
        itemDomain = ItemDomain.builder()
                .description("Teste")
                .qty(1)
                .build();

        itemRequest = ItemRequestDTO.builder()
                .description("Teste")
                .qty(1)
                .build();

        inboundRequest = InboundRequestDTO.builder()
                .seller("Rafaela")
                .items(List.of(itemRequest))
                .build();
    }

    @Test
    public void shouldConverterInboundRequestToInboundDomain() {
        when(itemConverter.toDomain(itemRequest)).thenReturn(itemDomain);

        final InboundDomain inboundDomain = inboundRequestConverter.toDomain(inboundRequest);

        assertThat(inboundDomain.getSeller()).isEqualTo("Rafaela");
        assertTrue(inboundDomain.getItems().stream()
                        .allMatch(itemDomain ->
                                itemDomain.getDescription().equals("Teste")),
                "Teste");
        assertTrue(inboundDomain.getItems().stream()
                        .allMatch(itemDomain ->
                                itemDomain.getQty().equals(1)),
                "1");
        verify(itemConverter, times(1)).toDomain(itemRequest);
    }
}