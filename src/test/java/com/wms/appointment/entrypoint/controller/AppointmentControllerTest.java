package com.wms.appointment.entrypoint.controller;

import com.wms.appointment.entrypoint.controller.dtos.AppointmentRequestDTO;
import com.wms.appointment.entrypoint.controller.dtos.AppointmentResponseDTO;
import com.wms.appointment.infra.model.Item;
import com.wms.appointment.infra.model.Seller;
import com.wms.appointment.infra.repository.ItemRepository;
import com.wms.appointment.infra.repository.SellerRepository;
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
    private ItemRepository itemRepository;

    @Autowired
    private TestRestTemplate template;

    private Seller seller;

    private Item item1;

    private Item item2;

    @BeforeAll
    void init() {
        seller = new Seller("Gabriela", "92.170.591/0001-63");
        seller = sellerRepository.save(seller);

        item1 = new Item(1L, "fdfa5aa2-9d76-4909-83d5-8a79e90e34cg", "SKU-1");
        item2 = new Item(2L, "edfa5aa2-9d76-4909-83d5-8a79e90e34cf", "SKU-2");
        item1 = itemRepository.save(item1);
        item2 = itemRepository.save(item2);
    }

    @Test
    public void shouldCreateAppointment() {
        final List<String> itemsId = List.of(
                item1.getId().toString(),
                item2.getId().toString());

        final AppointmentRequestDTO appointmentRequest = new AppointmentRequestDTO(
                "2026-01-21T10:00",
                seller.getId().toString(),
                itemsId);

        final ResponseEntity<AppointmentResponseDTO> response = template.postForEntity(
                "/appointment",
                appointmentRequest,
                AppointmentResponseDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @ParameterizedTest
    @MethodSource("invalidAppointmentProvider")
    void shouldReturnBadRequestWhenInvalidAppointment(String appointmentAt,
                                                      String sellerId,
                                                      List<String> items
    ) {

        AppointmentRequestDTO appointmentRequest = new AppointmentRequestDTO(
                appointmentAt,
                sellerId,
                items
        );

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

                // lista vazia
                Arguments.of("2026-01-01", "5d49bbcf-adde-4bd6-9c4b-35a684875142", List.of(), "items"),

                // lista null
                Arguments.of("2026-01-01", "5d49bbcf-adde-4bd6-9c4b-35a684875142", null, "items"),

                // item inválido dentro da lista
                Arguments.of("2026-01-01", "5d49bbcf-adde-4bd6-9c4b-35a684875142", List.of(""), "items"),
                Arguments.of("2026-01-01", "5d49bbcf-adde-4bd6-9c4b-35a684875142", List.of(" "), "items")
        );
    }
}