package com.wms.inbound.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@AllArgsConstructor
public class InboundDomain {
    private String id;

    private String status;

    private List<ItemDomain> items;

    public InboundDomain(String id, String status) {
        this.id = id;
        this.status = status;
    }
}
