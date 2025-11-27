package com.wms.receiving.entrypoint.controller;

import com.wms.receiving.core.usecase.CreateInbound;
import com.wms.receiving.core.usecase.FindAllScheduledInbounds;
import com.wms.receiving.core.usecase.FindFirstInbound;
import com.wms.receiving.core.usecase.FindInboundByCode;
import com.wms.receiving.entrypoint.dtos.InboundRequestDTO;
import com.wms.receiving.entrypoint.dtos.InboundResponseDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/inbound")
public class InboundController {
    private final CreateInbound createInbound;
    private final FindInboundByCode findInboundByCode;
    private final FindAllScheduledInbounds findAllScheduledInbounds;
    private final FindFirstInbound findFirstInbound;

    @PostMapping
    public ResponseEntity<InboundResponseDTO> createInbound(@Valid @RequestBody final InboundRequestDTO inboundRequestDTO) {
        log.info("Creating inbound={}", inboundRequestDTO);
        return ResponseEntity.ok(createInbound.execute(inboundRequestDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<List<InboundResponseDTO>> findAllScheduledInbounds() {
        log.info("Finding all scheduled inbounds.");
        return ResponseEntity.ok(findAllScheduledInbounds.execute());
    }

    @GetMapping("/first")
    public ResponseEntity<InboundResponseDTO> findFirstInbound() {
        log.info("Finding first inbound.");
        return ResponseEntity.ok(findFirstInbound.execute());
    }

    @GetMapping("/{code}")
    public ResponseEntity<InboundResponseDTO> findInboundIdByCode(@PathVariable final String code) {
        log.info("Finding inboundId by code={}", code);
        return ResponseEntity.ok(findInboundByCode.execute(code));
    }
}

