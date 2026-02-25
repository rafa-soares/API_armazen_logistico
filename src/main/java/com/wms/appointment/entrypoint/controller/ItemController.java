package com.wms.appointment.entrypoint.controller;

import com.wms.appointment.core.usecase.CreateItem;
import com.wms.appointment.entrypoint.controller.dtos.ItemRequestDTO;
import com.wms.appointment.entrypoint.controller.dtos.ItemResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/item")
public class ItemController {
    public final CreateItem createItem;

    @PostMapping
    public ResponseEntity<ItemResponseDTO> createItem(@Valid @RequestBody final ItemRequestDTO itemRequest) {
        return ResponseEntity.ok(createItem.execute(itemRequest));
    }

}
