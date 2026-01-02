package com.wms.receiving.entrypoint.controller;

import com.wms.receiving.core.usecase.CreateInbound;
import com.wms.receiving.entrypoint.controller.dtos.InboundRequestDTO;
import com.wms.receiving.entrypoint.controller.dtos.InboundResponseDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    private final CreateInbound createInbound;

    @PostMapping("/inbound")
    public ResponseEntity<InboundResponseDTO> createInbound(@Valid @RequestBody final InboundRequestDTO inboundRequestDTO) {
        log.info("Creating inbound={}", inboundRequestDTO);
        return ResponseEntity.ok(createInbound.execute(inboundRequestDTO));
    }
}

