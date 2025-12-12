package com.wms.receiving.infra.model;

import com.wms.receiving.core.domain.ItemDomain;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Setter
@Getter
@Entity
@Table(name = "item")
public class Item {
    @Id
    private Long id;

    @NotNull
    private String description;

    private String sku;

    @NotNull
    private Integer qty;

    @Enumerated(EnumType.STRING)
    private Status statusChecking = Status.OPEN;

//    @NotNull
    @ManyToOne
    @JoinColumn(name = "inbound_id")
    private Inbound inbound;

    public static ItemDomain toDomain(final Item item) {
        final ItemDomain itemDomain = new ItemDomain();
        itemDomain.setId(item.getId());
        itemDomain.setDescription(item.getDescription());
        itemDomain.setSku(item.getSku());
        itemDomain.setQty(item.getQty());
        itemDomain.setStatusChecking(item.getStatusChecking());
        return itemDomain;
    }

    public void setId() {
        this.id = generateRandomId();
    }

    private Long generateRandomId() {
        final Random random = new Random();
        final String id = String.format("%04d", random.nextInt(10000));
        return Long.parseLong(id);
    }
}
