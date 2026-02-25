package com.wms.appointment.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@AllArgsConstructor
public class InboundDomain {
    private String id;

    private String Status;

    private List<ItemDomain> items;
}
