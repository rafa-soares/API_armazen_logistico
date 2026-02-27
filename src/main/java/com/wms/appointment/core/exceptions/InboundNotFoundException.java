package com.wms.appointment.core.exceptions;

import java.util.List;
import java.util.UUID;

public class InboundNotFoundException extends RuntimeException {
    private static final String MESSAGE = "inbound ids not found: %s";

    public InboundNotFoundException(final List<UUID> uuids) {
        super(String.format(MESSAGE, uuids));
    }
}
