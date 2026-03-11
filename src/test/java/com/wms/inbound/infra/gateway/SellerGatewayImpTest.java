package com.wms.inbound.infra.gateway;

import com.wms.inbound.core.domain.SellerDomain;
import com.wms.inbound.core.exceptions.SellerNotFoundException;
import com.wms.inbound.infra.mapper.SellerMapper;
import com.wms.inbound.infra.model.Seller;
import com.wms.inbound.infra.repository.SellerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SellerGatewayImpTest {
    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private SellerMapper sellerMapper;

    @InjectMocks
    private SellerGatewayImp sellerGatewayImp;

    private Seller seller;

    private SellerDomain sellerDomainInput;

    private SellerDomain sellerDomainOutput;

    @BeforeEach
    void setUp() {
        seller = new Seller(
                "Gabriela",
                "92.170.591/0001-63");

        sellerDomainInput = new SellerDomain(
                null,
                "Gabriela",
                "92.170.591/0001-63");

        sellerDomainOutput = new SellerDomain(
                "5d49bbcf-adde-4bd6-9c4b-35a684875142",
                "Gabriela",
                "92.170.591/0001-63");
    }

    @Test
    public void shouldCreateAppointment() {
        when(sellerRepository.save(seller)).thenReturn(seller);
        when(sellerMapper.toEntity(sellerDomainInput)).thenReturn(seller);
        when(sellerMapper.toDomain(seller)).thenReturn(sellerDomainOutput);

        final SellerDomain sellerDomain = sellerGatewayImp.save(sellerDomainInput);

        assertThat(sellerDomain.getId()).isEqualTo("5d49bbcf-adde-4bd6-9c4b-35a684875142");
        assertThat(sellerDomain.getName()).isEqualTo("Gabriela");
        assertThat(sellerDomain.getCnpj()).isEqualTo("92.170.591/0001-63");
        verify(sellerMapper, times(1)).toDomain(seller);
    }

    @Test
    public void shouldFindSellerById() {
        when(sellerRepository.findById(UUID.fromString("5d49bbcf-adde-4bd6-9c4b-35a684875142"))).thenReturn(Optional.of(seller));
        when(sellerMapper.toDomain(seller)).thenReturn(sellerDomainOutput);

        final SellerDomain sellerDomain = sellerGatewayImp.findById("5d49bbcf-adde-4bd6-9c4b-35a684875142");

        assertThat(sellerDomain.getId()).isEqualTo("5d49bbcf-adde-4bd6-9c4b-35a684875142");
        assertThat(sellerDomain.getName()).isEqualTo("Gabriela");
        assertThat(sellerDomain.getCnpj()).isEqualTo("92.170.591/0001-63");
        verify(sellerMapper, times(1)).toDomain(seller);
    }

    @Test
    public void sellerNotFoundException() {
        when(sellerRepository.findById(UUID.fromString("5d49bbcf-adde-4bd6-9c4b-35a684875141"))).thenReturn(Optional.empty());

        assertThrows(SellerNotFoundException.class, () ->
                sellerGatewayImp.findById("5d49bbcf-adde-4bd6-9c4b-35a684875141"));

        verifyNoInteractions(sellerMapper);
    }
}