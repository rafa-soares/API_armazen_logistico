package com.wms.appointment.core.usecase;

import com.wms.appointment.core.domain.SellerDomain;
import com.wms.appointment.core.gateway.SellerGateway;
import com.wms.appointment.entrypoint.controller.dtos.SellerRequestDTO;
import com.wms.appointment.entrypoint.controller.dtos.SellerResponseDTO;
import com.wms.appointment.infra.mapper.SellerMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateSellerTest {
    @Mock
    SellerGateway sellerGateway;

    @Mock
    private SellerMapper sellerMapper;

    @InjectMocks
    private CreateSeller createSeller;

    @Test
    public void shouldCreateSeller() {
        final SellerResponseDTO sellerResponse = new SellerResponseDTO(
                "5d49bbcf-adde-4bd6-9c4b-35a684875142",
                "Gabriela",
                "92.170.591/0001-63");

        final SellerRequestDTO sellerRequest = new SellerRequestDTO(
                "Gabriela",
                "92.170.591/0001-63");

        final SellerDomain sellerDomain = new SellerDomain(
                null,
                "Gabriela",
                "92.170.591/0001-63");

        when(sellerMapper.toDomain(sellerRequest)).thenReturn(sellerDomain);
        when(sellerMapper.toResponse(sellerDomain)).thenReturn(sellerResponse);
        when(sellerGateway.save(sellerDomain)).thenReturn(sellerDomain);

        final SellerResponseDTO sellerResponseResult = createSeller.execute(sellerRequest);

        assertThat(sellerResponseResult.id()).isEqualTo("5d49bbcf-adde-4bd6-9c4b-35a684875142");
        assertThat(sellerResponseResult.name()).isEqualTo("Gabriela");
        assertThat(sellerResponseResult.cnpj()).isEqualTo("92.170.591/0001-63");
        verify(sellerMapper, times(1)).toResponse(sellerDomain);
    }
}