package com.wms.appointment.core.usecase;

import com.wms.appointment.core.domain.AppointmentDomain;
import com.wms.appointment.core.domain.InboundDomain;
import com.wms.appointment.core.domain.ItemDomain;
import com.wms.appointment.core.domain.SellerDomain;
import com.wms.appointment.core.gateway.InboundGateway;
import com.wms.appointment.core.gateway.SellerGateway;
import com.wms.appointment.entrypoint.controller.dtos.*;
import com.wms.appointment.infra.gateway.AppointmentGatewayImp;
import com.wms.appointment.infra.mapper.AppointmentMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateAppointmentTest {
    @Mock
    private AppointmentMapper appointmentMapper;

    @Mock
    private InboundGateway inboundGateway;

    @Mock
    private SellerGateway sellerGateway;

    @Mock
    private AppointmentGatewayImp appointmentGateway;

    @InjectMocks
    private CreateAppointment createAppointment;

    @Test
    void shouldCreateAppointment() {
        final ItemResponseDTO itemResponse2 = ItemResponseDTO.builder()
                .id("edfa5aa2-9d76-4909-83d5-8a79e90e34cf")
                .quantity(2L)
                .description("Iphone")
                .status("PENDING")
                .build();

        final ItemResponseDTO itemResponse1 = ItemResponseDTO.builder()
                .id("fdfa5aa2-9d76-4909-83d5-8a79e90e34cg")
                .quantity(1L)
                .description("Macbook")
                .status("PENDING")
                .build();

        final InboundResponseDTO inboundResponse = InboundResponseDTO.builder()
                .id("6945bc81-1348-4374-9597-3a9760e71bb6")
                .status("SCHEDULED")
                .items(List.of(itemResponse1, itemResponse2))
                .build();

        final SellerResponseDTO sellerResponse = SellerResponseDTO.builder()
                .id("5d49bbcf-adde-4bd6-9c4b-35a684875142")
                .name("Rafaela")
                .cnpj("92.170.591/0001-63")
                .build();

        final AppointmentResponseDTO appointmentResponse = AppointmentResponseDTO.builder()
                .id("7d49bbcf-adde-4bd6-9c4b-35a684875986")
                .appointmentAt("2026-01-21T10:00")
                .seller(sellerResponse)
                .inbounds(List.of(inboundResponse))
                .build();

        final ItemDomain itemDomain2 = new ItemDomain(
                "edfa5aa2-9d76-4909-83d5-8a79e90e34cf",
                2L,
                "Iphone",
                "PENDING");

        final ItemDomain itemDomain1 = new ItemDomain(
                "fdfa5aa2-9d76-4909-83d5-8a79e90e34cg",
                1L,
                "Macbook",
                "PENDING");

        final InboundDomain inboundDomain = new InboundDomain(
                "6945bc81-1348-4374-9597-3a9760e71bb6",
                "SCHEDULED",
                List.of(itemDomain1, itemDomain2));

        final SellerDomain sellerDomain = new SellerDomain(
                "5d49bbcf-adde-4bd6-9c4b-35a684875142",
                "Rafaela",
                "92.170.591/0001-63");

        final AppointmentDomain appointmentDomainOutput = new AppointmentDomain(
                "7d49bbcf-adde-4bd6-9c4b-35a684875986",
                "2026-01-21T10:00",
                null,
                sellerDomain,
                null,
                List.of(inboundDomain));

        final AppointmentDomain appointmentDomainInput = new AppointmentDomain(
                null,
                "2026-01-21T10:00",
                "5d49bbcf-adde-4bd6-9c4b-35a684875142",
                null,
                List.of("6945bc81-1348-4374-9597-3a9760e71bb6"),
                null);

        final AppointmentRequestDTO appointmentRequest = new AppointmentRequestDTO(
                "2026-01-21T10:00",
                "5d49bbcf-adde-4bd6-9c4b-35a684875142",
                List.of("6945bc81-1348-4374-9597-3a9760e71bb6"));

        when(sellerGateway.findById("5d49bbcf-adde-4bd6-9c4b-35a684875142")).thenReturn(sellerDomain);
        when(inboundGateway.findAllById(appointmentRequest.inbounds())).thenReturn(List.of(inboundDomain));
        when(appointmentMapper.toDomain(appointmentRequest)).thenReturn(appointmentDomainInput);
        when(appointmentGateway.save(appointmentDomainInput)).thenReturn(appointmentDomainOutput);
        when(appointmentMapper.toResponse(appointmentDomainOutput)).thenReturn(appointmentResponse);

        final AppointmentResponseDTO appointmentResponseResult = createAppointment.execute(appointmentRequest);

        assertThat(appointmentResponseResult.id()).isEqualTo("7d49bbcf-adde-4bd6-9c4b-35a684875986");
        assertThat(appointmentResponseResult.appointmentAt()).isEqualTo("2026-01-21T10:00");
        assertThat(appointmentResponseResult.seller()).isEqualTo(sellerResponse);
        assertTrue(appointmentResponseResult.inbounds().stream()
                .allMatch(inbound -> inbound.id().equals("6945bc81-1348-4374-9597-3a9760e71bb6")),
                        "6945bc81-1348-4374-9597-3a9760e71bb6");
        verify(appointmentMapper, times(1)).toResponse(appointmentDomainOutput);
    }
}