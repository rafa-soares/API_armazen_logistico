package com.wms.inbound.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class AppointmentDomain {
    private String id;

    private String appointmentAt;

    final String sellerId;

    private SellerDomain seller;

    private List<String> inbounds;

    private List<InboundDomain> inboundsDomain;
}
