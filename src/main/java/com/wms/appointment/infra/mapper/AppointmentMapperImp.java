package com.wms.appointment.infra.mapper;

import com.wms.appointment.core.domain.AppointmentDomain;
import com.wms.appointment.core.domain.ItemDomain;
import com.wms.appointment.core.domain.SellerDomain;
import com.wms.appointment.entrypoint.controller.dtos.AppointmentRequestDTO;
import com.wms.appointment.entrypoint.controller.dtos.AppointmentResponseDTO;
import com.wms.appointment.entrypoint.controller.dtos.ItemResponseDTO;
import com.wms.appointment.entrypoint.controller.dtos.SellerResponseDTO;
import com.wms.appointment.infra.model.Appointment;
import com.wms.appointment.infra.model.Item;
import com.wms.appointment.infra.model.Seller;
import com.wms.appointment.infra.repository.ItemRepository;
import com.wms.appointment.infra.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class AppointmentMapperImp implements AppointmentMapper {
    public final SellerRepository sellerRepository;
    public final ItemRepository itemRepository;
    public final SellerMapper sellerMapper;
    public final ItemMapper itemMapper;
    public final InboundMapper inboundMapper;

    @Override
    public AppointmentDomain toDomain(AppointmentRequestDTO appointmentRequest, SellerDomain sellerDomain, List<ItemDomain> itemDomain) {
        return new AppointmentDomain(null, appointmentRequest.appointmentAt(), sellerDomain, itemDomain);
    }

    @Override
    public Appointment toEntity(AppointmentDomain appointmentDomain) {
        final LocalDateTime appointmentAt = LocalDateTime.parse(appointmentDomain.getAppointmentAt());

        final Seller seller = sellerRepository
                .getReferenceById(UUID.fromString(appointmentDomain.getSeller().getId()));

        final List<Item> items = appointmentDomain.getItems().stream()
                .map(itemDomain -> {
                    final Item item = itemRepository.getReferenceById(UUID.fromString(itemDomain.getId()));
                    return item;
                })
                .toList();

        return new Appointment(appointmentAt, seller, items);
    }

    @Override
    public AppointmentDomain toDomain(Appointment appointment) {
        final String appointmentAt = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(appointment.getAppointmentAt());

        final SellerDomain sellerDomain = sellerMapper.toDomain(appointment.getSeller());

//        final List<ItemDomain> itemsDomain = itemMapper.toDomain(appointment.getInbound());

        final

        return new AppointmentDomain(appointment.getId().toString(), appointmentAt, sellerDomain, itemsDomain);
    }

    @Override
    public AppointmentResponseDTO toResponse(AppointmentDomain appointmentDomain) {
        final SellerResponseDTO sellerResponse = sellerMapper.toResponse(appointmentDomain.getSeller());

        final List<ItemResponseDTO> itemsResponse = itemMapper.toResponse(appointmentDomain.getItems());

        return new AppointmentResponseDTO(appointmentDomain.getId(), appointmentDomain.getAppointmentAt(), sellerResponse, itemsResponse);
    }
}
