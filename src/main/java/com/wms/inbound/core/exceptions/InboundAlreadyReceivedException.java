package com.wms.inbound.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InboundAlreadyReceivedException extends RuntimeException {
    private static final String MESSAGE = "Inbound already received.";

    public InboundAlreadyReceivedException() {
        super(MESSAGE);
    }
}