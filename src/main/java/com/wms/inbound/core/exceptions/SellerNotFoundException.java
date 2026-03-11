package com.wms.inbound.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SellerNotFoundException extends RuntimeException {
    private static final String MESSAGE = "seller_id= %s not found.";

    public SellerNotFoundException(String sellerId) {
        super(String.format(MESSAGE, sellerId));
    }
}
