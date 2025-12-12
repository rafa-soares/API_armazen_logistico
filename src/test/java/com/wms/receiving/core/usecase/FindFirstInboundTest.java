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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindFirstInboundTest {
    @Mock
    private ReceivingGatewayImp receivingGatewayImp;

    @InjectMocks
    private FindFirstInbound findFirstInbound;

    @Test
    public void shouldFindFirstInbound() {
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

        when(receivingGatewayImp.findFirstInbound()).thenReturn(inboundDomain);

        InboundResponseDTO inboundResponse = findFirstInbound.execute();

        assertThat(inboundResponse.getStatusChecking()).isEqualTo(Status.OPEN);
        assertThat(inboundResponse.getItems()).isEqualTo(List.of(itemResponse));
        verify(receivingGatewayImp, times(1)).findFirstInbound();
    }
}