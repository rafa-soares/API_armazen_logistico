package com.wms.inbound.core.exceptions;

import java.util.UUID;

public class AppointmentNotFoundException extends RuntimeException {
    private static final String MESSAGE = "appointment id not found: %s";


    public AppointmentNotFoundException(final UUID uuid) {
        super(String.format(MESSAGE, uuid));
    }
}
