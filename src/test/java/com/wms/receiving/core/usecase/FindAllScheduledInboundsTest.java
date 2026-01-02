package com.wms.receiving.core.usecase;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.core.domain.ItemDomain;
import com.wms.receiving.entrypoint.controller.dtos.InboundResponseDTO;
import com.wms.receiving.entrypoint.controller.dtos.ItemResponseDTO;
import com.wms.receiving.entrypoint.mapper.domainToResponse.InboundDomainToInboundResponseConverter;
import com.wms.receiving.infra.gateway.ReceivingGatewayImp;
import com.wms.receiving.infra.model.Status;
import org.junit.jupiter.api.BeforeEach;
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

    @Mock
    private InboundDomainToInboundResponseConverter inboundDomainConverter;

    @InjectMocks
    private FindAllScheduledInbounds findAllScheduledInbounds;

    private ItemDomain itemDomain;

    private InboundDomain inboundDomain;

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
                .seller("Rafaela")
                .statusChecking(Status.OPEN)
                .code("IS_73_09_76_39")
                .items(List.of(itemDomain))
                .build();

        itemResponse.setDescription("Iphone");
        itemResponse.setQty(1);
        itemResponse.setStatusChecking(Status.OPEN);

        inboundResponse.setStatusReceiving(Status.PENDING);
        inboundResponse.setStatusChecking(Status.OPEN);
        inboundResponse.setCode("IS_73_09_76_39");
        inboundResponse.setItems(List.of(itemResponse));
    }

    @Test
    public void shouldFindAllScheduledInbounds() {
        when(inboundDomainConverter.toResponse(List.of(inboundDomain))).thenReturn(List.of(inboundResponse));
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