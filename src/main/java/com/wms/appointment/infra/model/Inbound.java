package com.wms.appointment.infra.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@ToString
@Getter
@Entity
@Table(name = "inbound")
public class Inbound {
    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Status status = Status.SCHEDULED;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @OneToMany(mappedBy = "inbound")
    private List<Item> items;

    protected Inbound() {
    }

    public Inbound(final List<Item> items) {
        this.items = items;
    }
}