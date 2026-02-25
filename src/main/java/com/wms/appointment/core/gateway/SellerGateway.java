package com.wms.appointment.core.gateway;

import com.wms.appointment.core.domain.SellerDomain;

public interface SellerGateway {
    SellerDomain save(SellerDomain sellerDomain);

    SellerDomain findById(String id);
}
