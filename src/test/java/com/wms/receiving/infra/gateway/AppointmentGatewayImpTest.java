package com.wms.receiving.infra.gateway;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.core.domain.ItemDomain;
import com.wms.receiving.infra.mapper.domainToModel.InboundDomainToInboundConverter;
import com.wms.receiving.infra.mapper.modelToDomain.InboundToInboundDomainConverter;
import com.wms.receiving.infra.model.Inbound;
import com.wms.receiving.infra.model.Item;
import com.wms.receiving.infra.model.Status;
import com.wms.receiving.infra.repository.InboundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentGatewayImpTest {
    @Mock
    private InboundRepository inboundRepository;

    @InjectMocks
    private AppointmentGatewayImp inboundGatewayImp;

    @Mock
    private InboundDomainToInboundConverter inboundDomainConverter;

    @Mock
    private InboundToInboundDomainConverter inboundConverter;


    private Inbound inbound;

    private Item item;

    private InboundDomain inboundDomain;

    private ItemDomain itemDomain;

    @BeforeEach
    void setUp() {
        item = new Item();
        item.setDescription("Carro");
        item.setQty(1);
        item.setStatusChecking(Status.OPEN);

        inbound = new Inbound();
        inbound.setSeller("Nathália");
        inbound.setStatusChecking(Status.OPEN);
        inbound.setCode("IS_73_09_76_39");
        inbound.setItems(List.of(item));

        itemDomain = ItemDomain.builder()
                .description("Carro")
                .qty(1)
                .statusChecking(Status.OPEN)
                .build();

        inboundDomain = InboundDomain.builder()
                .seller("Nathália")
                .statusChecking(Status.OPEN)
                .code("IS_73_09_76_39")
                .items(List.of(itemDomain))
                .build();
    }

    @Test
    public void shouldCreateInbound() {
        when(inboundDomainConverter.toInbound(inboundDomain)).thenReturn(inbound);
        when(inboundConverter.toDomain(inbound)).thenReturn(inboundDomain);
        when(inboundRepository.save(any(Inbound.class))).thenReturn(inbound);

        final InboundDomain inboundResponse = inboundGatewayImp.saveInbound(inboundDomain);

        assertEquals(inbound.getStatusChecking(), inboundResponse.getStatusChecking());
        assertEquals(inbound.getCode(), inboundResponse.getCode());
        verify(inboundRepository, times(1)).save(any(Inbound.class));
    }
}