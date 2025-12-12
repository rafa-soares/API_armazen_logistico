package com.wms.receiving.core.domain;

import com.wms.receiving.entrypoint.controller.dtos.InboundResponseDTO;
import com.wms.receiving.infra.model.Inbound;
import com.wms.receiving.infra.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InboundDomainTest {
    private ItemDomain itemDomain;

    private InboundDomain inboundDomain;

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
    }

    @Test
    void shouldConvertInboundDomainToInbound() {
        final Inbound inbound = InboundDomain.toInbound(inboundDomain);

        assertThat(inbound.getSeller()).isEqualTo("Teste");
        assertThat(inbound.getStatusChecking()).isEqualTo(Status.OPEN);
    }

    @Test
    void shouldConvertInboundDomainToInboundResponse() {
        final InboundResponseDTO inboundResponse = InboundDomain.toResponse(inboundDomain);

        assertThat(inboundResponse.getStatusChecking()).isEqualTo(Status.OPEN);
        assertThat(inboundResponse.getCode()).isEqualTo("123");
        assertTrue(inboundResponse.getItems().stream()
                        .allMatch(itemResponseDTO -> itemResponseDTO.getDescription().equals("Teste")),
                "Teste");
    }

    @Test
    void shouldConverterInboundDomainListToInboundResponseList() {
        final List<InboundResponseDTO> inboundsResponse = InboundDomain.toResponse(List.of(inboundDomain));

        assertTrue(inboundsResponse.stream()
                        .allMatch(inboundsResponseDTO -> inboundsResponseDTO.getCode().equals("123")),
                "123");
    }
}