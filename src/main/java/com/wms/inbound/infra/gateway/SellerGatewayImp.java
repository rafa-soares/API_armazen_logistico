package com.wms.inbound.infra.gateway;

import com.wms.inbound.core.domain.SellerDomain;
import com.wms.inbound.core.exceptions.SellerNotFoundException;
import com.wms.inbound.core.gateway.SellerGateway;
import com.wms.inbound.infra.mapper.SellerMapper;
import com.wms.inbound.infra.model.Seller;
import com.wms.inbound.infra.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class SellerGatewayImp implements SellerGateway {
    private final SellerRepository sellerRepository;
    private final SellerMapper sellerMapper;

    @Override
    public SellerDomain save(final SellerDomain sellerDomain) {
        log.info("[save] Saving seller. {}", sellerDomain);
        final Seller seller = sellerMapper.toEntity(sellerDomain);

        return sellerMapper.toDomain(sellerRepository.save(seller));
    }

    @Override
    public SellerDomain findById(final String id) {
        log.info("[findById] Finding seller by id={}", id);

        final Seller seller = sellerRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new SellerNotFoundException(id));
        log.info("[findById] Seller found: {}", seller);

        return sellerMapper.toDomain(seller);
    }
}
