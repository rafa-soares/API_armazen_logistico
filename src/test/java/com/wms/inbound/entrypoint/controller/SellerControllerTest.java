package com.wms.inbound.entrypoint.controller;

import com.wms.inbound.entrypoint.controller.dtos.SellerRequestDTO;
import com.wms.inbound.entrypoint.controller.dtos.SellerResponseDTO;
import com.wms.inbound.infra.repository.SellerRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SellerControllerTest {
    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private TestRestTemplate template;

    @AfterAll
    void init() {
        sellerRepository.deleteAll();
    }

    @Test
    public void shouldCreateSeller() {
        final SellerRequestDTO sellerRequest = new SellerRequestDTO(
                "Gabriela",
                "92.170.591/0001-63");

        ResponseEntity<SellerResponseDTO> response = template.postForEntity(
                "/seller",
                sellerRequest,
                SellerResponseDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @ParameterizedTest
    @CsvSource({
            "'', 92.170.591/0001-63",
            "Gabriela, ''",
            "' ', 92.170.591/0001-63",
            "Gabriela, ' '"})
    void shouldReturnBadRequestWhenSellerOrCnpjIsInvalid(String name, String cnpj) {
        final SellerRequestDTO sellerRequest = new SellerRequestDTO(
                name,
                cnpj);

        ResponseEntity<SellerResponseDTO> response = template.postForEntity("/seller",
                sellerRequest, SellerResponseDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}