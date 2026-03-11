package com.wms.inbound.core.gateway;

import com.wms.inbound.core.domain.SellerDomain;

public interface SellerGateway {
    SellerDomain save(SellerDomain sellerDomain);

    SellerDomain findById(String id);
}
