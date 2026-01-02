package com.wms.receiving.core.usecase;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.core.domain.ItemDomain;
import com.wms.receiving.core.gateway.ReceivingGateway;
import com.wms.receiving.entrypoint.controller.dtos.InboundResponseDTO;
import com.wms.receiving.entrypoint.controller.dtos.ItemResponseDTO;
import com.wms.receiving.entrypoint.mapper.domainToResponse.InboundDomainToInboundResponseConverter;
import com.wms.receiving.infra.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InboundReceivingStatusUpdateTest {
    @Mock
    private ReceivingGateway receivingGateway;

    @Mock
    private ApplicationEventPublisher publisher;

    @Mock
    private InboundDomainToInboundResponseConverter inboundDomainConverter;

    @InjectMocks
    private InboundReceivingStatusUpdate inboundReceivingStatusUpdate;

    private ItemDomain itemDomain;

    private InboundDomain inboundDomain;

    private InboundResponseDTO inboundResponse = new InboundResponseDTO();

    private ItemResponseDTO itemResponse = new ItemResponseDTO();

    @BeforeEach
    void setUp() {
        itemDomain = ItemDomain.builder()
                .description("Carro")
                .qty(1)
                .statusChecking(Status.OPEN)
                .build();

        inboundDomain = InboundDomain.builder()
                .id(12345678L)
                .statusReceiving(Status.RECEIVED)
                .seller("Nathália")
                .code("IS_73_09_76_39")
                .items(List.of(itemDomain))
                .build();

        itemResponse.setDescription("Carro");
        itemResponse.setQty(1);
        itemResponse.setStatusChecking(Status.OPEN);

        inboundResponse.setId(12345678L);
        inboundResponse.setStatusReceiving(Status.RECEIVED);
        inboundResponse.setCode("IS_73_09_76_39");
        inboundResponse.setItems(List.of());
    }

    @Test
    public void shouldUpdateStatusInbound() {
        when(inboundDomainConverter.toResponse(inboundDomain)).thenReturn(inboundResponse);
        when(receivingGateway.updateStatusInbound(12345678L)).thenReturn(inboundDomain);

        final InboundResponseDTO inboundResponseDTO = inboundReceivingStatusUpdate.execute(12345678L);

        assertEquals(Status.RECEIVED, inboundResponseDTO.getStatusReceiving());
        verify(receivingGateway, times(1)).updateStatusInbound(12345678L);
    }
}