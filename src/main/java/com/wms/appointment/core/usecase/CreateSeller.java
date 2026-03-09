package com.wms.appointment.core.usecase;

import com.wms.appointment.core.domain.SellerDomain;
import com.wms.appointment.core.gateway.SellerGateway;
import com.wms.appointment.entrypoint.controller.dtos.SellerRequestDTO;
import com.wms.appointment.entrypoint.controller.dtos.SellerResponseDTO;
import com.wms.appointment.infra.mapper.SellerMapper;
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
