package com.wms.receiving.core.domain;

import com.wms.receiving.infra.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDomain {
    private Long id;

    private String description;

    private String sku;

    private Integer qty;

    private Status statusChecking;
}
