package com.wms.inbound.infra.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Getter
@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(length = 36)
    private UUID id;

    @NotNull
    @Column(nullable = false)
    private Long quantity;

    @NotNull
    @Column(nullable = false)
    private String description;

//    @NotNull
//    @Column(nullable = false)
//    private String sku;

    @Enumerated(EnumType.STRING)
    private StatusItem status = StatusItem.PENDING;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inbound_id")
    private Inbound inbound;

    protected Item() {
    }

    public Item(Long quantity, String description) {
        this.quantity = quantity;
        this.description = description;
    }
}
