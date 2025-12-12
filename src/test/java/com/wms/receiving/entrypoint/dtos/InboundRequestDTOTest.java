package com.wms.receiving.entrypoint.dtos;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.entrypoint.controller.dtos.InboundRequestDTO;
import com.wms.receiving.entrypoint.controller.dtos.ItemRequestDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InboundRequestDTOTest {

    @Test
    public void shouldConvertInboundRequestToInboundDomain() {
        ItemRequestDTO itemRequest = ItemRequestDTO.builder()
                .description("Teste")
                .qty(1)
                .build();

        InboundRequestDTO inboundRequest = InboundRequestDTO.builder()
                .seller("Teste")
                .items(List.of(itemRequest))
                .build();

        final InboundDomain inboundResponse = InboundRequestDTO.toDomain(inboundRequest);

        assertThat(inboundResponse.getSeller()).isEqualTo("Teste");
        assertTrue(inboundResponse.getItems().stream()
                        .allMatch(inboundDomain -> inboundDomain.getDescription().equals("Teste")),
                "Teste");
    }
}