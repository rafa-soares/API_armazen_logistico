package com.wms.inbound.entrypoint.controller;

import com.wms.inbound.entrypoint.controller.dtos.InboundResponseDTO;
import com.wms.inbound.core.usecase.InboundReceivingStatusUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/receiving")
public class ReceivingController {
    private final InboundReceivingStatusUpdate inboundReceivingStatusUpdate;

    @PutMapping("/beep-inbound/{id}")
    public ResponseEntity<InboundResponseDTO> beepInbound(@PathVariable final String id) {
        log.info("[beepInbound] Inbound receiving beep.");
        return ResponseEntity.ok(inboundReceivingStatusUpdate.execute(id));
    }
}
