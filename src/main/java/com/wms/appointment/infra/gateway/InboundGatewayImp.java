package com.wms.appointment.infra.gateway;

import com.wms.appointment.core.domain.InboundDomain;
import com.wms.appointment.core.exceptions.InboundNotFoundException;
import com.wms.appointment.core.gateway.InboundGateway;
import com.wms.appointment.infra.mapper.InboundMapper;
import com.wms.appointment.infra.model.Inbound;
import com.wms.appointment.infra.model.Item;
import com.wms.appointment.infra.repository.InboundRepository;
import com.wms.appointment.infra.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class InboundGatewayImp implements InboundGateway {
    public final InboundRepository inboundRepository;
    public final ItemRepository itemRepository;
    public final InboundMapper inboundMapper;

    @Override
    public InboundDomain save(InboundDomain inboundDomain) {
        log.info("[save] Saving inbound. {}", inboundDomain);
        final List<Item> items = inboundDomain.getItems().stream()
                .map(itemDomain -> itemRepository.getReferenceById(
                        UUID.fromString(itemDomain.getId())))
                .toList();

        final Inbound inbound = inboundMapper.toEntity(items);

        final Inbound inboundResult = inboundRepository.save(inbound);

        return inboundMapper.toDomain(inboundResult);
    }

    @Override
    public List<InboundDomain> findAllById(List<String> ids) {
        final List<UUID> uuidList = ids.stream()
                .map(id -> UUID.fromString(id))
                .toList();

        final List<Inbound> inbounds = inboundRepository.findAllById(uuidList);

        validateIds(inbounds, uuidList);

        final List<InboundDomain> inboundsDomain = inbounds.stream()
                .map(inbound -> inboundMapper.toDomain(inbound))
                .toList();

        return inboundsDomain;
    }

    private static void validateIds(List<Inbound> inbounds, List<UUID> uuidList) {
        if (inbounds.size() != uuidList.size()) {

            final Set<UUID> foundIds = inbounds.stream()
                    .map(inbound -> inbound.getId())
                    .collect(Collectors.toSet());

            List<UUID> missingIds = uuidList.stream()
                    .filter(uuid -> !foundIds.contains(uuid))
                    .toList();

            throw new InboundNotFoundException(missingIds);
        }
    }
}
