package com.wms.inbound.infra.repository;

import com.wms.inbound.infra.model.Inbound;
import com.wms.inbound.infra.model.StatusInbound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface ReceivingRepository extends JpaRepository<Inbound, UUID> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("""
            update Inbound i
            set i.status = :status
            where i.id = :inboundId
            and i.status = 'SCHEDULED'
            """)
    int updateStatus(@Param("inboundId") UUID inboundId,
                      @Param("status") StatusInbound status);
}