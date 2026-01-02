package com.wms.receiving.infra.listener.event;

import com.wms.receiving.core.domain.ItemDomain;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InboundReceivedEvent {
    private Long id;

    private String statusReceiving;

    private List<ItemDomain> items;
}
