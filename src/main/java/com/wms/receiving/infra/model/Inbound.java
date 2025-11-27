package com.wms.receiving.infra.model;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.core.domain.ItemDomain;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Setter
@Getter
@Entity
@Table(name = "inbound")
public class Inbound {
    @Id
    private Long id;

    @NotNull
    private String seller;

    private String code;

    @Enumerated(EnumType.STRING)
    private Status status = Status.OPEN;

    @NotNull
    @OneToMany(mappedBy = "inbound", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Item> items;

    public void setId() {
        this.id = generateRandomId();
    }

    public void setCode() {
        this.code = generateRandomCode();
    }

    private Long generateRandomId() {
        final Random random = new Random();
        final String id = String.format("%08d", random.nextInt(100000000));
        return Long.parseLong(id);
    }

    private String generateRandomCode() {
        final Random random = new Random();

        final String part1 = String.format("%02d", random.nextInt(100));
        final String part2 = String.format("%02d", random.nextInt(100));
        final String part3 = String.format("%02d", random.nextInt(100));
        final String part4 = String.format("%02d", random.nextInt(100));

        return String.format("IS_%s_%s_%s_%s", part1, part2, part3, part4);
    }

    public static InboundDomain toDomain(final Inbound inbound) {
        final InboundDomain inboundDomain = new InboundDomain();
        inboundDomain.setId(inbound.getId());
        inboundDomain.setSeller(inbound.getSeller());
        inboundDomain.setCode(inbound.getCode());
        inboundDomain.setStatus(inbound.getStatus());

        final List<ItemDomain> items = inbound.getItems().stream()
                .map(Item::toDomain)
                .collect(Collectors.toList());

        inboundDomain.setItems(items);

        return inboundDomain;
    }

    public static List<InboundDomain> toDomain(final List<Inbound> inboundList) {
        return inboundList.stream()
                .map(Inbound::toDomain)
                .collect(Collectors.toList());
    }
}
