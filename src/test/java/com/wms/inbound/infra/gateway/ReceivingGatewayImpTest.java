package com.wms.inbound.infra.gateway;

import com.wms.inbound.core.exceptions.InboundAlreadyReceivedException;
import com.wms.inbound.core.exceptions.InboundNotFoundException;
import com.wms.inbound.core.exceptions.InvalidInboundStatusException;
import com.wms.inbound.infra.model.Inbound;
import com.wms.inbound.infra.model.Item;
import com.wms.inbound.infra.model.StatusInbound;
import com.wms.inbound.infra.repository.ReceivingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceivingGatewayImpTest {

    @Mock
    private ReceivingRepository receivingRepository;

    @InjectMocks
    private ReceivingGatewayImp receivingGatewayImp;

    @Test
    public void shouldUpdateStatusInbound() {
        final String id = "9c8423f4-df21-4e38-9e19-7f243d9c27b9";

        when(receivingRepository.updateStatus(UUID.fromString(id), StatusInbound.RECEIVED)).thenReturn(1);

        receivingGatewayImp.updateStatusInbound(id);

        verify(receivingRepository, times(1))
                .updateStatus(UUID.fromString(id), StatusInbound.RECEIVED);
    }

    @Test
    public void shouldReturnInboundNotFoundException() {
        final String id = "9c8423f4-df21-4e38-9e19-7f243d9c27b9";

        when(receivingRepository.updateStatus(UUID.fromString(id), StatusInbound.RECEIVED)).thenReturn(0);
        when(receivingRepository.findById(UUID.fromString(id))).thenThrow(InboundNotFoundException.class);

        assertThrows(InboundNotFoundException.class, () -> receivingGatewayImp.updateStatusInbound(id));

        verify(receivingRepository, times(1))
                .findById(UUID.fromString(id));
    }

    @Test
    public void shouldReturnInboundAlreadyReceivedException() {
        final String id = "9c8423f4-df21-4e38-9e19-7f243d9c27b9";

        final Item item = new Item(2L, "Planner");

        final Inbound inbound = new Inbound(List.of(item));
        inbound.setStatus(StatusInbound.RECEIVED);

        when(receivingRepository.updateStatus(UUID.fromString(id), StatusInbound.RECEIVED)).thenReturn(0);
        when(receivingRepository.findById(UUID.fromString(id))).thenReturn(Optional.of(inbound));

        assertThrows(InboundAlreadyReceivedException.class, () -> receivingGatewayImp.updateStatusInbound(id));

        assertThat(inbound.getStatus()).isEqualTo(StatusInbound.RECEIVED);
        verify(receivingRepository, times(1))
                .findById(UUID.fromString(id));
    }

    @Test
    public void shouldInvalidInboundStatusExceptionException() {
        final String id = "9c8423f4-df21-4e38-9e19-7f243d9c27b9";

        final Item item = new Item(2L, "Planner");

        final Inbound inbound = new Inbound(List.of(item));

        when(receivingRepository.updateStatus(UUID.fromString(id), StatusInbound.RECEIVED)).thenReturn(0);
        when(receivingRepository.findById(UUID.fromString(id))).thenReturn(Optional.of(inbound));

        assertThrows(InvalidInboundStatusException.class, () -> receivingGatewayImp.updateStatusInbound(id));

        assertThat(inbound.getStatus()).isEqualTo(StatusInbound.PENDING_SCHEDULING);
        verify(receivingRepository, times(1))
                .findById(UUID.fromString(id));
    }
}