//package com.wms.receiving.core.usecase;
//
//import com.wms.receiving.core.domain.InboundDomain;
//import com.wms.receiving.core.domain.ItemDomain;
//import com.wms.receiving.core.gateway.ReceivingGateway;
//import com.wms.receiving.entrypoint.controller.dtos.InboundResponseDTO;
//import com.wms.receiving.infra.model.Status;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class InboundReceivingStatusUpdateTest {
//    @Mock
//    private ReceivingGateway receivingGateway;
//
//    @InjectMocks
//    private InboundReceivingStatusUpdate inboundReceivingStatusUpdate;
//
//    @Test
//    public void shouldUpdateStatusInbound() {
//        final ItemDomain itemDomain = ItemDomain.builder()
//                .description("Carro")
//                .qty(1)
//                .statusChecking(Status.OPEN)
//                .build();
//
//        final InboundDomain inboundDomain = InboundDomain.builder()
//                .id(12345678L)
//                .statusReceiving(Status.RECEIVED)
//                .seller("Nathália")
//                .code("IS_73_09_76_39")
//                .items(List.of(itemDomain))
//                .build();
//
//        when(receivingGateway.updateStatusInbound(12345678L)).thenReturn(inboundDomain);
//
//        final InboundResponseDTO inboundResponseDTO = inboundReceivingStatusUpdate.execute(12345678L);
//
//        assertEquals(Status.RECEIVED, inboundResponseDTO.getStatusReceiving());
//        verify(receivingGateway, times(1)).updateStatusInbound(12345678L);
//    }
//}