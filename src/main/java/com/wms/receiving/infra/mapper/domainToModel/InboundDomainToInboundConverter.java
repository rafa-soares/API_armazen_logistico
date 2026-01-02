package com.wms.receiving.infra.mapper.domainToModel;

import com.wms.receiving.core.domain.InboundDomain;
import com.wms.receiving.infra.model.Inbound;

public interface InboundDomainToInboundConverter {
    Inbound toInbound(final InboundDomain inboundDomain);
}
