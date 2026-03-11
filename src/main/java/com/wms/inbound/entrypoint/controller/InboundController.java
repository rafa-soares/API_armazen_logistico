package com.wms.inbound.entrypoint.controller;

import com.wms.inbound.core.usecase.CreateInbound;
import com.wms.inbound.entrypoint.controller.dtos.InboundRequestDTO;
import com.wms.inbound.entrypoint.controller.dtos.InboundResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/inbound")
public class InboundController {
    private final CreateInbound createInbound;

    @PostMapping
    public ResponseEntity<InboundResponseDTO> createInbound(@Valid @RequestBody final InboundRequestDTO inboundRequest) {
        log.info("[createAppointment] Creating inbound= {}", inboundRequest);
        return ResponseEntity.ok(createInbound.execute(inboundRequest));
    }
}
