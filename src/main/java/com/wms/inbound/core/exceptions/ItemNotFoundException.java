package com.wms.inbound.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends RuntimeException {
    private static final String MESSAGE = "item ids not found: %s";

    public ItemNotFoundException(final List<UUID> ids) {
        super(String.format(MESSAGE, ids));
    }
}
