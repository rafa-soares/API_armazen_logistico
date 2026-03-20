package com.wms.inbound.core.usecase;

import com.wms.inbound.core.domain.InboundDomain;
import com.wms.inbound.core.domain.ItemDomain;
import com.wms.inbound.core.gateway.InboundGateway;
import com.wms.inbound.core.gateway.ReceivingGateway;
import com.wms.inbound.entrypoint.controller.dtos.InboundResponseDTO;
import com.wms.inbound.infra.model.StatusInbound;
import com.wms.inbound.infra.model.StatusItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InboundReceivingStatusUpdateTest {

    @Mock
    private InboundGateway inboundGateway;

    @Mock
    private ReceivingGateway receivedGateway;

    @InjectMocks
    private InboundReceivingStatusUpdate inboundReceivingStatusUpdate;

    @Test
    public void shouldInboundReceivingStatusUpdate() {
        final String inboundId = "9c8423f4-df21-4e38-9e19-7f243d9c27b9";

        final ItemDomain itemDomain = new ItemDomain(
                "1a8423f4-df21-4e38-9e19-7f243d9c272b",
                1L,
                "Sabão",
                StatusItem.PENDING.toString());

        final InboundDomain inboundDomain = new InboundDomain(
                inboundId,
                StatusInbound.RECEIVED.toString(),
                List.of(itemDomain));

        doNothing().when(receivedGateway).updateStatusInbound(inboundId);
        when(inboundGateway.findById(inboundId)).thenReturn(inboundDomain);

        final InboundResponseDTO inboundResponse = inboundReceivingStatusUpdate.execute(inboundId);

        assertThat(inboundResponse.id()).isEqualTo(inboundId);
        assertThat(inboundResponse.status()).isEqualTo("RECEIVED");

        verify(inboundGateway, times(1)).findById(inboundId);
    }
}