package com.wms.receiving.entrypoint.controller;

import com.wms.receiving.core.usecase.CreateInbound;
import com.wms.receiving.core.usecase.FindAllScheduledInbounds;
import com.wms.receiving.core.usecase.FindFirstInbound;
import com.wms.receiving.core.usecase.FindInboundByCode;
import com.wms.receiving.entrypoint.dtos.InboundRequestDTO;
import com.wms.receiving.entrypoint.dtos.InboundResponseDTO;
import com.wms.receiving.entrypoint.dtos.ItemRequestDTO;
import com.wms.receiving.infra.repository.InboundRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@TestInstance(PER_CLASS)
@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InboundControllerTest {
    @Autowired
    private InboundRepository inboundRepository;

    @MockBean
    private CreateInbound createInbound;

    @MockBean
    private FindAllScheduledInbounds findAllScheduledInbounds;

    @MockBean
    private FindFirstInbound findFirstInbound;

    @MockBean
    private FindInboundByCode findInboundByCode;

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
                template.postForEntity("/inbound", inboundRequest, InboundResponseDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(createInbound, times(1)).execute(any(InboundRequestDTO.class));
    }

    @Test
    public void shouldFindAllScheduledInbounds() {
        ResponseEntity<List<InboundResponseDTO>> response =
                template.getForEntity("/inbound/all", null, List.of(InboundResponseDTO.class));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(findAllScheduledInbounds, times(1)).execute();
    }

    @Test
    public void shouldGetFindFirstInbound() {
        ResponseEntity<InboundResponseDTO> response =
                template.getForEntity("/inbound/first", null, InboundResponseDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(findFirstInbound, times(1)).execute();
    }

    @Test
    public void shouldGetFindInboundIdByCode() {
        ResponseEntity<InboundResponseDTO> response =
                template.getForEntity("/inbound/{code}", InboundResponseDTO.class, "1234");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(findInboundByCode, times(1)).execute("1234");
    }
}