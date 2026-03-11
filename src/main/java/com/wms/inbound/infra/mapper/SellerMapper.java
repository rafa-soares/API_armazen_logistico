package com.wms.inbound.infra.mapper;

import com.wms.inbound.core.domain.SellerDomain;
import com.wms.inbound.entrypoint.controller.dtos.SellerRequestDTO;
import com.wms.inbound.entrypoint.controller.dtos.SellerResponseDTO;
import com.wms.inbound.infra.model.Seller;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SellerMapper {
    SellerDomain toDomain(SellerRequestDTO sellerRequest);

    Seller toEntity(SellerDomain sellerDomain);

    SellerDomain toDomain(Seller seller);

    SellerResponseDTO toResponse(SellerDomain sellerDomain);
}
