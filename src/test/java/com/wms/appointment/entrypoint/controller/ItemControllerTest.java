package com.wms.appointment.entrypoint.controller;

import com.wms.appointment.core.usecase.CreateItem;
import com.wms.appointment.entrypoint.controller.dtos.ItemRequestDTO;
import com.wms.appointment.entrypoint.controller.dtos.ItemResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItemControllerTest {
    @Autowired
    private CreateItem createItem;

    @Autowired
    private TestRestTemplate template;

    @Test
    public void shouldCreateItem() {
        final ItemRequestDTO itemRequest = new ItemRequestDTO(
                2L,
                "Patins",
                "1234");

        final ResponseEntity<ItemResponseDTO> response = template.postForEntity(
                "/item",
                itemRequest,
                ItemResponseDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @ParameterizedTest
    @CsvSource({
            "2, Macbook, ''",
            "2, ' ', '1234'",
            " , Macbook, '1234'"})
    void shouldReturnBadRequestWhenQuantityOrDescriptionOrSkuIsInvalid(final Long quantity, final String description, final String sku) {
        final ItemRequestDTO itemRequest = new ItemRequestDTO(
                quantity,
                description,
                sku);

        final ResponseEntity<ItemResponseDTO> response = template.postForEntity(
                "/item",
                itemRequest,
                ItemResponseDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}