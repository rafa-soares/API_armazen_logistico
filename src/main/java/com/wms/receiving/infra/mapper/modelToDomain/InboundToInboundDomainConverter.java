package com.wms.receiving.infra.mapper.modelToDomain;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.infra.model.Inbound;

import java.util.List;

public interface InboundToInboundDomainConverter {

    InboundDomain toDomain(final Inbound inbound);

    List<InboundDomain> toDomain(final List<Inbound> inboundList);
}
