package com.wms.appointment.infra.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@ToString
@Getter
@Entity
@Table(name = "seller")
public class Seller {
    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false, unique = true, updatable = false)
    private String cnpj;

    protected Seller() {
    }

    public Seller(String name, String cnpj) {
        this.name = name;
        this.cnpj = cnpj;
    }
}