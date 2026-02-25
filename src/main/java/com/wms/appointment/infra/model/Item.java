package com.wms.appointment.infra.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@ToString
@Getter
@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(nullable = false)
    private Long quantity;

    @NotNull
    @Column(nullable = false)
    private String description;

    @NotNull
    @Column(nullable = false)
    private String sku;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inbound_id")
    private Inbound inbound;

    protected Item() {
    }

    public Item(Long quantity, String description, String sku) {
        this.quantity = quantity;
        this.description = description;
        this.sku = sku;
    }
}
