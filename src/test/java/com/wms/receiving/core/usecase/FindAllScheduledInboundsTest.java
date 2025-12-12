package com.wms.receiving.core.usecase;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.core.domain.ItemDomain;
import com.wms.receiving.entrypoint.controller.dtos.InboundResponseDTO;
import com.wms.receiving.entrypoint.controller.dtos.ItemResponseDTO;
import com.wms.receiving.infra.gateway.ReceivingGatewayImp;
import com.wms.receiving.infra.model.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindAllScheduledInboundsTest {
    @Mock
    private ReceivingGatewayImp receivingGatewayImp;

    @InjectMocks
    private FindAllScheduledInbounds findAllScheduledInbounds;

    @Test
    public void shouldFindAllScheduledInbounds() {
        final ItemResponseDTO itemResponse = new ItemResponseDTO();
        itemResponse.setDescription("Iphone");
        itemResponse.setQty(1);
        itemResponse.setStatusChecking(Status.OPEN);

        final ItemDomain itemDomain = ItemDomain.builder()
                .description("Iphone")
                .qty(1)
                .statusChecking(Status.OPEN)
                .build();

        final InboundDomain inboundDomain = InboundDomain.builder()
                .seller("Rafaela")
                .statusChecking(Status.OPEN)
                .items(List.of(itemDomain))
                .build();

        when(receivingGatewayImp.findAllInbounds()).thenReturn(List.of(inboundDomain));

        List<InboundResponseDTO> inboundResponse = findAllScheduledInbounds.execute();

        assertTrue(inboundResponse.stream()
                        .allMatch(inboundResponseDTO -> inboundResponseDTO.getStatusChecking().equals(Status.OPEN)),
                "OPEN");
        assertTrue(inboundResponse.stream()
                        .allMatch(inboundResponseDTO -> inboundResponseDTO.getItems().equals(List.of(itemResponse))),
                List.of(itemResponse).toString());
        verify(receivingGatewayImp, times(1)).findAllInbounds();
    }
}