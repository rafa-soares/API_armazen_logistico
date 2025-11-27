package com.wms.receiving.core.usecase;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.core.domain.ItemDomain;
import com.wms.receiving.core.gateway.InboundGateway;
import com.wms.receiving.entrypoint.dtos.InboundRequestDTO;
import com.wms.receiving.entrypoint.dtos.InboundResponseDTO;
import com.wms.receiving.entrypoint.dtos.ItemRequestDTO;
import com.wms.receiving.infra.model.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateInboundTest {
    @Mock
    private InboundGateway inboundGateway;

    @InjectMocks
    private CreateInbound createInbound;

    @Test
    public void shouldCreateInbound() {
        final ItemRequestDTO itemRequest = ItemRequestDTO.builder()
                .description("Iphone")
                .build();

        final InboundRequestDTO inboundRequestDTO = InboundRequestDTO.builder()
                .seller("Rafaela")
                .items(List.of(itemRequest))
                .build();

        final ItemDomain itemDomain = ItemDomain.builder()
                .description("Iphone")
                .qty(1)
                .status(Status.OPEN)
                .build();

        final InboundDomain inboundDomain = InboundDomain.builder()
                .seller("Rafaela")
                .status(Status.OPEN)
                .items(List.of(itemDomain))
                .build();

        when(inboundGateway.saveInbound(any(InboundDomain.class))).thenReturn(inboundDomain);

        final InboundResponseDTO inboundResponse = createInbound.execute(inboundRequestDTO);

        assertEquals(inboundDomain.getStatus(), inboundResponse.getStatus());
        assertEquals(itemDomain.getDescription(), itemRequest.getDescription());
        verify(inboundGateway, times(1)).saveInbound(any(InboundDomain.class));
    }
}