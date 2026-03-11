package com.wms.inbound.entrypoint.controller;

import com.wms.inbound.entrypoint.controller.dtos.AppointmentRequestDTO;
import com.wms.inbound.entrypoint.controller.dtos.AppointmentResponseDTO;
import com.wms.inbound.infra.model.Inbound;
import com.wms.inbound.infra.model.Item;
import com.wms.inbound.infra.model.Seller;
import com.wms.inbound.infra.repository.InboundRepository;
import com.wms.inbound.infra.repository.SellerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AppointmentControllerTest {
    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private InboundRepository inboundRepository;

    @Autowired
    private TestRestTemplate template;

    private Seller seller;

    private Inbound inbound;

    private Item item1;

    private Item item2;

    @BeforeAll
    void beforeAll() {
        seller = new Seller("Gabriela", "92.170.591/0001-61");
        seller = sellerRepository.save(seller);

        item1 = new Item(1L, "Iphone");
        item2 = new Item(2L, "Planner");

        inbound = new Inbound(List.of(item1, item2));
        inboundRepository.save(inbound);
    }

    @Test
    public void shouldCreateAppointment() {
        final List<String> inboundsId = List.of(
                inbound.getId().toString());

        final AppointmentRequestDTO appointmentRequest = new AppointmentRequestDTO(
                "2026-01-21T10:00",
                seller.getId().toString(),
                inboundsId);

        final ResponseEntity<AppointmentResponseDTO> response = template.postForEntity(
                "/appointment",
                appointmentRequest,
                AppointmentResponseDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @ParameterizedTest
    @MethodSource("invalidAppointmentProvider")
    public void shouldReturnBadRequestWhenInvalidAppointment(String appointmentAt,
                                                      String sellerId,
                                                      List<String> inbounds) {

        AppointmentRequestDTO appointmentRequest = new AppointmentRequestDTO(
                appointmentAt,
                sellerId,
                inbounds);

        final ResponseEntity<AppointmentResponseDTO> response = template.postForEntity(
                "/appointment",
                appointmentRequest,
                AppointmentResponseDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    static Stream<Arguments> invalidAppointmentProvider() {
        return Stream.of(
                // appointmentAt inválido
                Arguments.of(null, "5d49bbcf-adde-4bd6-9c4b-35a684875142", List.of("fdfa5aa2-9d76-4909-83d5-8a79e90e34cg", "edfa5aa2-9d76-4909-83d5-8a79e90e34cf"), "appointmentAt"),
                Arguments.of("", "5d49bbcf-adde-4bd6-9c4b-35a684875142", List.of("fdfa5aa2-9d76-4909-83d5-8a79e90e34cg", "edfa5aa2-9d76-4909-83d5-8a79e90e34cf"), "appointmentAt"),
                Arguments.of(" ", "seller-1", List.of("fdfa5aa2-9d76-4909-83d5-8a79e90e34cg", "edfa5aa2-9d76-4909-83d5-8a79e90e34cf"), "appointmentAt"),

                // sellerId inválido
                Arguments.of("2026-01-01", null, List.of("fdfa5aa2-9d76-4909-83d5-8a79e90e34cg", "edfa5aa2-9d76-4909-83d5-8a79e90e34cf"), "sellerId"),
                Arguments.of("2026-01-01", "", List.of("fdfa5aa2-9d76-4909-83d5-8a79e90e34cg", "edfa5aa2-9d76-4909-83d5-8a79e90e34cf"), "sellerId"),

                // lista de inbounds vazia
                Arguments.of("2026-01-01", "5d49bbcf-adde-4bd6-9c4b-35a684875142", List.of(), "inbounds"),

                // lista de inbounds null
                Arguments.of("2026-01-01", "5d49bbcf-adde-4bd6-9c4b-35a684875142", null, "inbounds"),

                // inbounds inválido dentro da lista
                Arguments.of("2026-01-01", "5d49bbcf-adde-4bd6-9c4b-35a684875142", List.of(""), "inbounds"),
                Arguments.of("2026-01-01", "5d49bbcf-adde-4bd6-9c4b-35a684875142", List.of(" "), "inbounds")
        );
    }
}