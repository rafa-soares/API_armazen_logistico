package com.wms.inbound.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class ItemDomain {
    private String id;

    private Long quantity;

    private String description;

//    private String sku;

    private String status;
}
