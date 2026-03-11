package com.wms.inbound.infra.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Getter
@Entity
@Table(name = "inbound")
public class Inbound {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(length = 36)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50)
    private StatusInbound status = StatusInbound.SCHEDULED;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @OneToMany(mappedBy = "inbound", cascade = CascadeType.ALL)
    private List<Item> items;

    protected Inbound() {
    }

    public Inbound(final List<Item> items) {
        this.items = items;
        items.forEach(item -> item.setInbound(this));
    }
}