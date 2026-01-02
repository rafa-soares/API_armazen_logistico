package com.wms.receiving.entrypoint.mapper.domainToResponse;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.core.domain.ItemDomain;
import com.wms.receiving.entrypoint.controller.dtos.InboundResponseDTO;
import com.wms.receiving.entrypoint.controller.dtos.ItemResponseDTO;
import com.wms.receiving.infra.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InboundDomainToInboundResponseConverterImplTest {
    @InjectMocks
    private InboundDomainToInboundResponseConverterImpl inboundConverter;

    @Mock
    private ItemDomainToItemResponseConverterImpl itemConverter;

    private ItemDomain itemDomain;

    private InboundDomain inboundDomain;

    private ItemResponseDTO itemResponse = new ItemResponseDTO();

    @BeforeEach
    void setUp() {
        itemDomain = ItemDomain.builder()
                .description("Teste")
                .qty(1)
                .statusChecking(Status.OPEN)
                .build();

        inboundDomain = InboundDomain.builder()
                .code("123")
                .seller("Teste")
                .statusChecking(Status.OPEN)
                .items(List.of(itemDomain))
                .build();

        itemResponse.setDescription("Teste");
        itemResponse.setQty(1);
        itemResponse.setStatusChecking(Status.OPEN);
    }

    @Test
    void shouldConvertInboundDomainToInboundResponse() {
        when(itemConverter.toResponse(itemDomain)).thenReturn(itemResponse);

        final InboundResponseDTO inboundResponse = inboundConverter.toResponse(inboundDomain);

        assertThat(inboundResponse.getStatusChecking()).isEqualTo(Status.OPEN);
        assertThat(inboundResponse.getCode()).isEqualTo("123");
        assertTrue(inboundResponse.getItems().stream()
                        .allMatch(itemResponseDTO -> itemResponseDTO.getDescription().equals("Teste")),
                "Teste");
    }

    @Test
    void shouldConverterInboundDomainListToInboundResponseList() {
        when(itemConverter.toResponse(itemDomain)).thenReturn(itemResponse);

        final List<InboundResponseDTO> inboundsResponse = inboundConverter.toResponse(List.of(inboundDomain));

        assertTrue(inboundsResponse.stream()
                        .allMatch(inboundsResponseDTO -> inboundsResponseDTO.getCode().equals("123")),
                "123");
    }
}