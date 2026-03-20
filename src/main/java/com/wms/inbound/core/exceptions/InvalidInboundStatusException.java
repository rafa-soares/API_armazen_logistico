package com.wms.inbound.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidInboundStatusException extends RuntimeException {
    private static final String MESSAGE = "status= %s is invalid.";

    public InvalidInboundStatusException(String status) {
        super(String.format(MESSAGE, status));
    }
}