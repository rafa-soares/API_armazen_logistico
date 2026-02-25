package com.wms.appointment.infra.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ToString
@Getter
@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime appointmentAt;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    @OneToMany(mappedBy = "appointment")
    private List<Inbound> inbound;

    protected Appointment() {
    }

    public Appointment(LocalDateTime appointmentAt, Seller seller, List<Inbound> inbound) {
        this.appointmentAt = appointmentAt;
        this.seller = seller;
        this.inbound = inbound;
    }
}
