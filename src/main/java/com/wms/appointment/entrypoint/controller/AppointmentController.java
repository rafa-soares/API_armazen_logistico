package com.wms.appointment.entrypoint.controller;

import com.wms.appointment.core.usecase.CreateAppointment;
import com.wms.appointment.entrypoint.controller.dtos.AppointmentRequestDTO;
import com.wms.appointment.entrypoint.controller.dtos.AppointmentResponseDTO;
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
@RequestMapping("/appointment")
public class AppointmentController {
    private final CreateAppointment createAppointment;

    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> createAppointment(@Valid @RequestBody final AppointmentRequestDTO appointmentRequest) {
        log.info("[createAppointment] Creating date appointment= {}", appointmentRequest.appointmentAt());
        return ResponseEntity.ok(createAppointment.execute(appointmentRequest));
    }
}
