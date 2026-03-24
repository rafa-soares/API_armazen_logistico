package com.wms.inbound.entrypoint.controller;

import com.wms.inbound.core.usecase.CreateReceivingDocument;
import com.wms.inbound.core.usecase.InboundReceivingStatusUpdate;
import com.wms.inbound.entrypoint.controller.dtos.InboundResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/receiving")
public class ReceivingController {
    private final InboundReceivingStatusUpdate inboundReceivingStatusUpdate;
    private final CreateReceivingDocument createReceivingDocument;

    @GetMapping("/print/{appointmentId}")
    public ResponseEntity<Resource> print(@PathVariable final String appointmentId) {
        log.info("[print] Printing document receiving.");

        byte[] pdf = createReceivingDocument.execute(appointmentId);

        ByteArrayResource resource = new ByteArrayResource(pdf);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=receiving-document.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(pdf.length)
                .body(resource);
    }

    @PutMapping("/beep-inbound/{id}")
    public ResponseEntity<InboundResponseDTO> beepInbound(@PathVariable final String inboundId) {
        log.info("[beepInbound] Inbound receiving beep.");
        return ResponseEntity.ok(inboundReceivingStatusUpdate.execute(inboundId));
    }
}
