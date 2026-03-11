package com.wms.inbound.core.usecase;

import com.wms.inbound.core.domain.SellerDomain;
import com.wms.inbound.core.gateway.SellerGateway;
import com.wms.inbound.entrypoint.controller.dtos.SellerRequestDTO;
import com.wms.inbound.entrypoint.controller.dtos.SellerResponseDTO;
import com.wms.inbound.infra.mapper.SellerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreateSeller {
    public final SellerGateway sellerGateway;
    public final SellerMapper sellerMapper;

    public SellerResponseDTO execute(final SellerRequestDTO sellerRequest) {
        final SellerDomain sellerDomain = sellerMapper.toDomain(sellerRequest);

        final SellerDomain sellerResult = sellerGateway.save(sellerDomain);
        log.info("[execute] Seller save. {}", sellerResult);

        return sellerMapper.toResponse(sellerResult);
    }
}
