package com.wms.inbound.entrypoint.controller;

import com.wms.inbound.core.usecase.InboundReceivingStatusUpdate;
import com.wms.inbound.entrypoint.controller.dtos.AppointmentResponseDTO;
import com.wms.inbound.entrypoint.controller.dtos.InboundResponseDTO;
import com.wms.inbound.infra.model.Inbound;
import com.wms.inbound.infra.model.Item;
import com.wms.inbound.infra.model.Seller;
import com.wms.inbound.infra.model.StatusInbound;
import com.wms.inbound.infra.repository.InboundRepository;
import com.wms.inbound.infra.repository.ReceivingRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReceivingControllerTest {
    @Autowired
    private ReceivingRepository receivingRepository;

    @Autowired
    private InboundRepository inboundRepository;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private InboundReceivingStatusUpdate inboundReceivingStatusUpdate;

    @AfterAll
    public void afterAll() {
        inboundRepository.deleteAll();
    }

    @Test
    public void shouldUpdateStatus() {
        final Item item1 = new Item(1L, "Iphone");
        final Item item2 = new Item(2L, "Planner");

        final Inbound inbound = new Inbound(List.of(item1, item2));
        final Inbound inboundSave = inboundRepository.save(inbound);
        inboundSave.setStatus(StatusInbound.SCHEDULED);

        final String inboundId = inboundSave.getId().toString();

        ResponseEntity<InboundResponseDTO> response =
                template.exchange(
                        "/receiving/beep-inbound/{inboundId}",
                        HttpMethod.PUT,
                        HttpEntity.EMPTY,
                        InboundResponseDTO.class,
                        inboundId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}