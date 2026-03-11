package com.wms.inbound.infra.repository;

import com.wms.inbound.infra.model.Inbound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InboundRepository extends JpaRepository<Inbound, UUID> {
}
