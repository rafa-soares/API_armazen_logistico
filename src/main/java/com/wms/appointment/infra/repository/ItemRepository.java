package com.wms.appointment.infra.repository;

import com.wms.appointment.infra.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {

//    List<Optional<Item>> findAllById(final List<UUID> ids);
}
