package com.wms.inbound.core.usecase;

import com.wms.inbound.core.domain.InboundDomain;
import com.wms.inbound.core.domain.ItemDomain;
import com.wms.inbound.core.gateway.InboundGateway;
import com.wms.inbound.core.gateway.ItemGateway;
import com.wms.inbound.entrypoint.controller.dtos.InboundRequestDTO;
import com.wms.inbound.entrypoint.controller.dtos.InboundResponseDTO;
import com.wms.inbound.entrypoint.controller.dtos.ItemResponseDTO;
import com.wms.inbound.infra.mapper.InboundMapper;
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
class CreateInboundTest {
    @Mock
    private InboundGateway inboundGateway;

    @Mock
    private ItemGateway itemGateway;

    @Mock
    private InboundMapper inboundMapper;

    @InjectMocks
    private CreateInbound createInbound;

    @Test
    public void shouldCreateInbound() {
        final ItemDomain itemDomain = new ItemDomain(
                "626cd032-f7df-479e-8580-0d2e54255d19",
                2L,
                "Macbook",
                "PENDING");

        final InboundDomain inboundDomainInput = new InboundDomain(
                null,
                null,
                List.of(itemDomain));

        final InboundDomain inboundDomainOutput = new InboundDomain(
                "98242e0d-f832-4901-af2b-045c742fca11",
                "SCHEDULED",
                List.of(itemDomain));

        final ItemResponseDTO itemResponse = ItemResponseDTO.builder()
                .id(itemDomain.getId())
                .quantity(itemDomain.getQuantity())
                .description(itemDomain.getDescription())
                .status(itemDomain.getStatus())
                .build();

        final InboundResponseDTO inboundResponse = InboundResponseDTO.builder()
                .id(inboundDomainOutput.getId())
                .status(inboundDomainOutput.getStatus())
                .items(List.of(itemResponse))
                .build();

        final InboundRequestDTO inboundRequest = InboundRequestDTO.builder()
                .items(List.of("626cd032-f7df-479e-8580-0d2e54255d19"))
                .build();

        when(itemGateway.findAllById(inboundRequest.items()))
                .thenReturn(List.of(itemDomain));
        when(inboundMapper.toDomain(List.of(itemDomain))).thenReturn(inboundDomainInput);
        when(inboundGateway.save(inboundDomainInput)).thenReturn(inboundDomainOutput);
        when(inboundMapper.toResponse(inboundDomainOutput)).thenReturn(inboundResponse);

        final InboundResponseDTO inboundResponseResult = createInbound.execute(inboundRequest);

        assertThat(inboundResponseResult.id()).isEqualTo("98242e0d-f832-4901-af2b-045c742fca11");
        assertThat(inboundResponseResult.status()).isEqualTo("SCHEDULED");
        assertTrue(inboundResponseResult.items().stream()
                .allMatch(itemResponseDTO -> itemResponseDTO.id().equals("626cd032-f7df-479e-8580-0d2e54255d19")),
                "626cd032-f7df-479e-8580-0d2e54255d19");
        verify(inboundMapper, times(1)).toResponse(inboundDomainOutput);
    }
}