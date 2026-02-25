package com.wms.appointment.entrypoint.controller.dtos;

import java.util.List;

public record InboundRequestDTO(List<ItemRequestDTO> item) {
}
