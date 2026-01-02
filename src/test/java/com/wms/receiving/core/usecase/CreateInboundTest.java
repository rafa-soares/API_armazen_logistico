package com.wms.receiving.core.usecase;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.core.domain.ItemDomain;
import com.wms.receiving.core.gateway.AppointmentGateway;
import com.wms.receiving.entrypoint.controller.dtos.InboundRequestDTO;
import com.wms.receiving.entrypoint.controller.dtos.InboundResponseDTO;
import com.wms.receiving.entrypoint.controller.dtos.ItemRequestDTO;
import com.wms.receiving.entrypoint.controller.dtos.ItemResponseDTO;
import com.wms.receiving.entrypoint.mapper.domainToResponse.InboundDomainToInboundResponseConverter;
import com.wms.receiving.entrypoint.mapper.requestToDomain.InboundRequestToInboundDomainConverter;
import com.wms.receiving.infra.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateInboundTest {
    @Mock
    private InboundRequestToInboundDomainConverter inboundRequestConverter;

    @Mock
    private InboundDomainToInboundResponseConverter inboundDomainConverter;

    @Mock
    private AppointmentGateway appointmentGateway;

    @InjectMocks
    private CreateInbound createInbound;

    private ItemDomain itemDomain;

    private InboundDomain inboundDomain;

    private ItemRequestDTO itemRequest;

    private InboundRequestDTO inboundRequest;

    private InboundResponseDTO inboundResponse = new InboundResponseDTO();

    private ItemResponseDTO itemResponse = new ItemResponseDTO();

    @BeforeEach
    void setUp() {
        itemDomain = ItemDomain.builder()
                .description("Iphone")
                .qty(1)
                .statusChecking(Status.OPEN)
                .build();

        inboundDomain = InboundDomain.builder()
                .code("123")
                .seller("Rafaela")
                .statusChecking(Status.OPEN)
                .items(List.of(itemDomain))
                .build();

        itemRequest = ItemRequestDTO.builder()
                .description("Iphone")
                .build();

        inboundRequest = InboundRequestDTO.builder()
                .seller("Rafaela")
                .items(List.of(itemRequest))
                .build();

        itemResponse.setDescription("Teste");
        itemResponse.setQty(1);
        itemResponse.setStatusChecking(Status.OPEN);

        inboundResponse.setStatusReceiving(Status.PENDING);
        inboundResponse.setStatusChecking(Status.OPEN);
        inboundResponse.setCode("123");
        inboundResponse.setItems(List.of(itemResponse));
    }

    @Test
    public void shouldCreateInbound() {
        when(inboundRequestConverter.toDomain(inboundRequest)).thenReturn(inboundDomain);
        when(inboundDomainConverter.toResponse(inboundDomain)).thenReturn(inboundResponse);
        when(appointmentGateway.saveInbound(any(InboundDomain.class))).thenReturn(inboundDomain);

        final InboundResponseDTO inboundResponse = createInbound.execute(inboundRequest);

        assertEquals(inboundDomain.getStatusChecking(), inboundResponse.getStatusChecking());
        assertEquals(itemDomain.getDescription(), itemRequest.getDescription());
        verify(appointmentGateway, times(1)).saveInbound(any(InboundDomain.class));
    }
}