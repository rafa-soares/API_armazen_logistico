package com.wms.appointment.infra.mapper;

import com.wms.appointment.core.domain.SellerDomain;
import com.wms.appointment.entrypoint.controller.dtos.SellerRequestDTO;
import com.wms.appointment.entrypoint.controller.dtos.SellerResponseDTO;
import com.wms.appointment.infra.model.Seller;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SellerMapper {
    SellerDomain toDomain(SellerRequestDTO sellerRequest);

    Seller toEntity(SellerDomain sellerDomain);

    SellerDomain toDomain(Seller seller);

    SellerResponseDTO toResponse(SellerDomain sellerDomain);
}
