package com.wms.appointment.infra.gateway;

import com.wms.appointment.core.domain.ItemDomain;
import com.wms.appointment.core.exceptions.ItemNotFoundException;
import com.wms.appointment.core.gateway.ItemGateway;
import com.wms.appointment.infra.mapper.ItemMapper;
import com.wms.appointment.infra.model.Item;
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
public class ItemGatewayImp implements ItemGateway {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Override
    public ItemDomain save(ItemDomain itemDomain) {
        log.info("[save] Saving item. {}", itemDomain);
        final Item item = itemMapper.toEntity(itemDomain);

        final Item itemResult = itemRepository.save(item);
        log.info("[save] Item save. {}", itemResult);

        return itemMapper.toDomain(itemResult);
    }

    @Override
    public List<ItemDomain> findAllById(List<String> ids) {
        log.info("[findAllById] Finding items by ids={}", ids);

        List<UUID> uuidList = ids.stream()
                .map(id ->  UUID.fromString(id))
                .toList();

        List<Item> items = itemRepository.findAllById(uuidList);

        validateIds(items, uuidList);

        log.info("[findAllById] Items found: {}", items);

        return itemMapper.toDomain(items);
    }

    private static void validateIds(List<Item> items, List<UUID> uuidList) {
        if (items.size() != uuidList.size()) {

            Set<UUID> foundIds = items.stream()
                    .map(item -> item.getId())
                    .collect(Collectors.toSet());

            List<UUID> missingIds = uuidList.stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();

            throw new ItemNotFoundException(missingIds);
        }
    }
}
