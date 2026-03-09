package com.wms.appointment.infra.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Entity
@Table(name = "inbound")
public class Inbound {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "VARCHAR(36)")
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Status status = Status.SCHEDULED;

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