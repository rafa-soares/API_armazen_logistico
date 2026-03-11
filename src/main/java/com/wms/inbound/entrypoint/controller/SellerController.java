package com.wms.inbound.entrypoint.controller;

import com.wms.inbound.core.usecase.CreateSeller;
import com.wms.inbound.entrypoint.controller.dtos.SellerRequestDTO;
import com.wms.inbound.entrypoint.controller.dtos.SellerResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/seller")
public class SellerController {
    public final CreateSeller createSeller;

    @PostMapping
    public ResponseEntity<SellerResponseDTO> createSeller(@Valid @RequestBody final SellerRequestDTO sellerRequest) {
        return ResponseEntity.ok(createSeller.execute(sellerRequest));
    }
}
