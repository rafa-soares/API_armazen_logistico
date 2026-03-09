package com.wms.appointment.infra.gateway;

import com.wms.appointment.core.domain.AppointmentDomain;
import com.wms.appointment.core.domain.InboundDomain;
import com.wms.appointment.core.domain.ItemDomain;
import com.wms.appointment.core.domain.SellerDomain;
import com.wms.appointment.infra.mapper.AppointmentMapper;
import com.wms.appointment.infra.model.Appointment;
import com.wms.appointment.infra.model.Inbound;
import com.wms.appointment.infra.model.Item;
import com.wms.appointment.infra.model.Seller;
import com.wms.appointment.infra.repository.AppointmentRepository;
import com.wms.appointment.infra.repository.InboundRepository;
import com.wms.appointment.infra.repository.SellerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentGatewayImpTest {
    @Mock
    public SellerRepository sellerRepository;

    @Mock
    public InboundRepository inboundRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private AppointmentMapper appointmentMapper;

    @InjectMocks
    private AppointmentGatewayImp appointmentGatewayImp;

    @Test
    public void shouldCreateAppointment() {
        final Item item = new Item(
                2L,
                "Iphone");


        final Seller seller = new Seller(
                "Gabriela",
                "92.170.591/0001-63");

        final Inbound inbound = new Inbound(
                List.of(item));

        final Appointment appointment = new Appointment(
                LocalDateTime.of(2026, 01, 21, 10, 00),
                seller,
                List.of(inbound));

        final SellerDomain sellerDomainOutput = new SellerDomain(
                "5d49bbcf-adde-4bd6-9c4b-35a684875142",
                "Gabriela",
                "92.170.591/0001-63");

        final ItemDomain itemDomainOutput = new ItemDomain(
                "edfa5aa2-9d76-4909-83d5-8a79e90e34cf",
                2L,
                "Iphone",
                "PENDING");

        final InboundDomain inboundDomainOutput = new InboundDomain(
                "6945bc81-1348-4374-9597-3a9760e71bb6",
                "SCHEDULED",
                List.of(itemDomainOutput));

        final AppointmentDomain appointmentDomainOutput = new AppointmentDomain(
                "7d49bbcf-adde-4bd6-9c4b-35a684875986",
                "2026-01-21T10:00",
                null,
                sellerDomainOutput,
                null,
                List.of(inboundDomainOutput));

        final AppointmentDomain appointmentDomainInput = new AppointmentDomain(
                null,
                "2026-01-21T10:00",
                "5d49bbcf-adde-4bd6-9c4b-35a684875142",
                null,
                List.of("6945bc81-1348-4374-9597-3a9760e71bb6"),
                null);

        when(sellerRepository.getReferenceById(UUID.fromString("5d49bbcf-adde-4bd6-9c4b-35a684875142")))
                .thenReturn(seller);
        when(inboundRepository.getReferenceById(UUID.fromString("6945bc81-1348-4374-9597-3a9760e71bb6")))
                .thenReturn(inbound);
        when(appointmentRepository.save(appointment)).thenReturn(appointment);
        when(appointmentMapper.toEntity(appointmentDomainInput, seller, List.of(inbound)))
                .thenReturn(appointment);
        when(appointmentMapper.toDomain(appointment)).thenReturn(appointmentDomainOutput);

        final AppointmentDomain appointmentDomainResult = appointmentGatewayImp.save(appointmentDomainInput);

        assertThat(appointmentDomainResult.getId()).isEqualTo("7d49bbcf-adde-4bd6-9c4b-35a684875986");
        assertThat(appointmentDomainResult.getAppointmentAt()).isEqualTo("2026-01-21T10:00");
        assertThat(appointmentDomainResult.getSeller().getId()).isEqualTo("5d49bbcf-adde-4bd6-9c4b-35a684875142");
        assertTrue(appointmentDomainResult.getInboundsDomain().stream()
                .allMatch(inboundDomain -> inboundDomain.getId().equals("6945bc81-1348-4374-9597-3a9760e71bb6")),
                "6945bc81-1348-4374-9597-3a9760e71bb6");
        verify(appointmentMapper, times(1)).toDomain(appointment);
    }
}