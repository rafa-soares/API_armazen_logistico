package com.wms.receiving.entrypoint.controller;

import com.wms.receiving.core.usecase.FindAllScheduledInbounds;
import com.wms.receiving.core.usecase.FindFirstInbound;
import com.wms.receiving.core.usecase.FindInboundByCode;
import com.wms.receiving.core.usecase.InboundReceivingStatusUpdate;
import com.wms.receiving.entrypoint.controller.dtos.InboundResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/receiving")
public class ReceivingController {
    private final FindInboundByCode findInboundByCode;
    private final FindAllScheduledInbounds findAllScheduledInbounds;
    private final FindFirstInbound findFirstInbound;
    private final InboundReceivingStatusUpdate inboundReceivingStatusUpdate;

    @GetMapping("/inbound/all")
    public ResponseEntity<List<InboundResponseDTO>> findAllScheduledInbounds() {
        log.info("Finding all scheduled inbounds.");
        return ResponseEntity.ok(findAllScheduledInbounds.execute());
    }

    @GetMapping("/inbound/first")
    public ResponseEntity<InboundResponseDTO> findFirstInbound() {
        log.info("Finding first inbound.");
        return ResponseEntity.ok(findFirstInbound.execute());
    }

    @GetMapping("/inbound/{code}")
    public ResponseEntity<InboundResponseDTO> findInboundIdByCode(@PathVariable final String code) {
        log.info("Finding inboundId by code={}", code);
        return ResponseEntity.ok(findInboundByCode.execute(code));
    }


    @PutMapping("/bip-inbound/{inbound}")
    public ResponseEntity<InboundResponseDTO> updateStatusInbound(@PathVariable final Long inbound) {
        log.info("Updating inbound receiving status.");
        return ResponseEntity.ok(inboundReceivingStatusUpdate.execute(inbound));
    }
}