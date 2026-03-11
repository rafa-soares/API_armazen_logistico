package com.wms.inbound.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class SellerDomain {
    private String id;

    private String name;

    private String cnpj;
}
