package com.wms.receiving.infra.repository;

import com.wms.receiving.infra.model.Inbound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InboundRepository extends JpaRepository<Inbound, Long> {

    Inbound findInboundByCode(String code);
}
