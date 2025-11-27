package com.wms.receiving.entrypoint.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InboundCreationException extends RuntimeException {
    public InboundCreationException(final ExceptionMessage message, final Throwable cause) {
        super(message.toString(), cause);
    }
}