package com.wms.inbound.entrypoint.controller;

import com.wms.inbound.entrypoint.controller.dtos.InboundRequestDTO;
import com.wms.inbound.entrypoint.controller.dtos.InboundResponseDTO;
import com.wms.inbound.infra.model.Item;
import com.wms.inbound.infra.repository.ItemRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InboundControllerTest {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private TestRestTemplate template;

    private Item item1;

    private Item item2;

    @BeforeAll
    void beforeAll() {
        item1 = new Item(1L, "Iphone");
        item2 = new Item(2L, "Planner");
        itemRepository.save(item1);
        itemRepository.save(item2);
    }

    @Test
    public void shouldCreateInbound() {
        final InboundRequestDTO inboundRequest = InboundRequestDTO.builder()
                .items(List.of(
                        item1.getId().toString(),
                        item2.getId().toString()))
                .build();

        final ResponseEntity<InboundResponseDTO> response = template.postForEntity(
                "/inbound",
                inboundRequest,
                InboundResponseDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @ParameterizedTest
    @CsvSource({
            "' ', Mackbook",
            "null, ' '"})
    public void shouldReturnBadRequestWhenInvalidItem(final String item1, final String item2) {
        final InboundRequestDTO inboundRequest = InboundRequestDTO.builder()
                .items(List.of(item1, item2))
                .build();

        final ResponseEntity<InboundResponseDTO> response = template.postForEntity(
                "/inbound",
                inboundRequest,
                InboundResponseDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}