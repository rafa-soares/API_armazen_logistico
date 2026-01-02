package com.wms.receiving.entrypoint.controller;

import com.wms.receiving.core.usecase.CreateInbound;
import com.wms.receiving.entrypoint.controller.dtos.InboundRequestDTO;
import com.wms.receiving.entrypoint.controller.dtos.InboundResponseDTO;
import com.wms.receiving.entrypoint.controller.dtos.ItemRequestDTO;
import com.wms.receiving.entrypoint.handler.ErrorResponse;
import com.wms.receiving.infra.repository.InboundRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AppointmentControllerTest {
    @Autowired
    private InboundRepository inboundRepository;

    @Autowired
    private CreateInbound createInbound;

    @Autowired
    private TestRestTemplate template;

    @BeforeAll
    public void init() {
        inboundRepository.deleteAll();
    }

    @Test
    public void shouldCreateInbound() {
        final ItemRequestDTO itemRequest = ItemRequestDTO.builder()
                .description("Macbook")
                .sku("123")
                .qty(3)
                .build();
        final InboundRequestDTO inboundRequest = InboundRequestDTO.builder()
                .seller("Madalena")
                .items(List.of(itemRequest))
                .build();

        ResponseEntity<InboundResponseDTO> response =
                template.postForEntity("/appointment/inbound", inboundRequest, InboundResponseDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void shouldReturnBadRequestWhenSellerIsNull() {
        final ItemRequestDTO itemRequest = ItemRequestDTO.builder()
                .description("Macbook")
                .sku("123")
                .qty(3)
                .build();
        final InboundRequestDTO inboundRequest = InboundRequestDTO.builder()
                .seller(null)
                .items(List.of(itemRequest))
                .build();

        ResponseEntity<ErrorResponse> response =
                template.postForEntity("/appointment/inbound", inboundRequest, ErrorResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void shouldReturnBadRequestWhenSellerIsEmpty() {
        final ItemRequestDTO itemRequest = ItemRequestDTO.builder()
                .description("Macbook")
                .sku("123")
                .qty(3)
                .build();
        final InboundRequestDTO inboundRequest = InboundRequestDTO.builder()
                .seller(" ")
                .items(List.of(itemRequest))
                .build();

        ResponseEntity<ErrorResponse> response =
                template.postForEntity("/appointment/inbound", inboundRequest, ErrorResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}