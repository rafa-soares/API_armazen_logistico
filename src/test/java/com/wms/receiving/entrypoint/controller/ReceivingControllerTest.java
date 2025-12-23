//package com.wms.receiving.entrypoint.controller;
//
//import com.wms.receiving.core.usecase.FindAllScheduledInbounds;
//import com.wms.receiving.core.usecase.FindFirstInbound;
//import com.wms.receiving.core.usecase.FindInboundByCode;
//import com.wms.receiving.core.usecase.InboundReceivingStatusUpdate;
//import com.wms.receiving.entrypoint.controller.dtos.InboundResponseDTO;
//import com.wms.receiving.entrypoint.handler.ErrorResponse;
//import com.wms.receiving.infra.model.Inbound;
//import com.wms.receiving.infra.model.Status;
//import com.wms.receiving.infra.repository.InboundRepository;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
//
//@TestInstance(PER_CLASS)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class ReceivingControllerTest {
//    @Autowired
//    private InboundRepository inboundRepository;
//
//    @Autowired
//    private FindAllScheduledInbounds findAllScheduledInbounds;
//
//    @Autowired
//    private FindFirstInbound findFirstInbound;
//
//    @Autowired
//    private FindInboundByCode findInboundByCode;
//
//    @Autowired
//    private InboundReceivingStatusUpdate inboundReceivingStatusUpdate;
//
//    @Autowired
//    private TestRestTemplate template;
//
//    @BeforeAll
//    public void init() {
//        inboundRepository.deleteAll();
//    }
//
//    @Test
//    public void shouldGetAllScheduledInbounds() {
//        ResponseEntity<List<InboundResponseDTO>> response =
//                template.getForEntity("/receiving/inbound/all", null, List.of(InboundResponseDTO.class));
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    public void shouldGetFindFirstInbound() {
//        Inbound inbound = new Inbound();
//        inbound.setId(12345678L);
//        inbound.setSeller("Nathália");
//        inbound.setCode("1234");
//        inbound.setStatusReceiving(Status.RECEIVED);
//
//        inboundRepository.save(inbound);
//
//        ResponseEntity<InboundResponseDTO> response =
//                template.getForEntity("/receiving/inbound/first", null, InboundResponseDTO.class);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    public void shouldGetFindInboundIdByCode() {
//        Inbound inbound = new Inbound();
//        inbound.setId(12345678L);
//        inbound.setSeller("Nathália");
//        inbound.setCode("1234");
//        inbound.setStatusReceiving(Status.RECEIVED);
//
//        inboundRepository.save(inbound);
//
//        ResponseEntity<InboundResponseDTO> response =
//                template.getForEntity(
//                        "/receiving/inbound/{code}",
//                        InboundResponseDTO.class,
//                        "1234");
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    public void shouldUpdateStatusInbound() {
//        Inbound inbound = new Inbound();
//        inbound.setId(12345678L);
//        inbound.setSeller("Nathália");
//        inbound.setStatusReceiving(Status.RECEIVED);
//
//        inboundRepository.save(inbound);
//
//        ResponseEntity<InboundResponseDTO> response =
//                template.exchange(
//                        "/receiving/bip-inbound/{inbound}",
//                        HttpMethod.PUT,
//                        HttpEntity.EMPTY,
//                        InboundResponseDTO.class,
//                        inbound.getId());
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    public void shouldInboundNotFound() {
//        ResponseEntity<ErrorResponse> response =
//                template.exchange(
//                        "/receiving/bip-inbound/{inbound}",
//                        HttpMethod.PUT,
//                        HttpEntity.EMPTY,
//                        ErrorResponse.class,
//                        1L);
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//    }
//}