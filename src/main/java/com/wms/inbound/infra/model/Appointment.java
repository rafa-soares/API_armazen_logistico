package com.wms.inbound.infra.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(length = 36)
    private UUID id;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime appointmentAt;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL)
    private List<Inbound> inbounds;

    protected Appointment() {
    }

    public Appointment(LocalDateTime appointmentAt, Seller seller, List<Inbound> inbounds) {
        this.appointmentAt = appointmentAt;
        this.seller = seller;
        this.inbounds = inbounds;
        inbounds.forEach(inbound -> {
            inbound.setAppointment(this);
            inbound.setStatus(StatusInbound.SCHEDULED);
        });
    }
}
