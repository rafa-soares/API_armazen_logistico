package com.wms.inbound.core.exceptions;

import java.util.List;
import java.util.UUID;

public class InboundNotFoundException extends RuntimeException {
    private static final String MESSAGE = "inbound id not found: %s";

    public InboundNotFoundException(final List<UUID> uuids) {
        super(String.format(MESSAGE, uuids));
    }

    public InboundNotFoundException(final UUID uuid) {
        super(String.format(MESSAGE, uuid));
    }
}
