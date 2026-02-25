package com.wms.appointment.infra.gateway;

import com.wms.appointment.core.domain.AppointmentDomain;
import com.wms.appointment.core.domain.ItemDomain;
import com.wms.appointment.core.domain.SellerDomain;
import com.wms.appointment.infra.mapper.AppointmentMapper;
import com.wms.appointment.infra.model.Appointment;
import com.wms.appointment.infra.model.Item;
import com.wms.appointment.infra.model.Seller;
import com.wms.appointment.infra.repository.AppointmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentGatewayImpTest {

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
                "Iphone",
                "1234");


        final Seller seller = new Seller(
                "Gabriela",
                "92.170.591/0001-63");

        final Appointment appointment = new Appointment(
                LocalDateTime.of(2026, 01, 21, 10, 00),
                seller,
                List.of(item));

        final SellerDomain sellerDomain = new SellerDomain(
                "5d49bbcf-adde-4bd6-9c4b-35a684875142",
                "Gabriela",
                "92.170.591/0001-63");

        final ItemDomain itemDomain = new ItemDomain(
                "edfa5aa2-9d76-4909-83d5-8a79e90e34cf",
                2L,
                "Iphone",
                "1234",
                "PENDING");

        final AppointmentDomain appointmentDomainInput = new AppointmentDomain(
                null,
                "2026-01-21T10:00",
                sellerDomain,
                List.of(itemDomain));


        final AppointmentDomain appointmentDomainOutput = new AppointmentDomain(
                "de4907b7-e5ff-485f-911f-76cf630ee236",
                "2026-01-21T10:00",
                sellerDomain,
                List.of(itemDomain));

        when(appointmentRepository.save(appointment)).thenReturn(appointment);
        when(appointmentMapper.toEntity(appointmentDomainInput)).thenReturn(appointment);
        when(appointmentMapper.toDomain(appointment)).thenReturn(appointmentDomainOutput);

        final AppointmentDomain appointmentDomainResult = appointmentGatewayImp.save(appointmentDomainInput);

        assertThat(appointmentDomainResult.getId()).isEqualTo("de4907b7-e5ff-485f-911f-76cf630ee236");
        assertThat(appointmentDomainResult.getAppointmentAt()).isEqualTo("2026-01-21T10:00");
        assertThat(appointmentDomainResult.getSeller().getId()).isEqualTo("5d49bbcf-adde-4bd6-9c4b-35a684875142");
        assertTrue(appointmentDomainResult.getItems().stream()
                .allMatch(itemDomainId -> itemDomainId.getId().equals("edfa5aa2-9d76-4909-83d5-8a79e90e34cf")),
                "edfa5aa2-9d76-4909-83d5-8a79e90e34cf");
        verify(appointmentMapper, times(1)).toDomain(appointment);
    }
}