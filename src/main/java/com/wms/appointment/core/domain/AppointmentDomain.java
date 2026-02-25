package com.wms.appointment.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@AllArgsConstructor
public class AppointmentDomain {
    private String id;

    private String appointmentAt;

    private SellerDomain seller;

    private List<ItemDomain> items;
}
