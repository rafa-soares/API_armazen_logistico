package com.wms.inbound.core.usecase;

import com.itextpdf.html2pdf.HtmlConverter;
import com.wms.inbound.core.domain.AppointmentDomain;
import com.wms.inbound.core.domain.ItemDomain;
import com.wms.inbound.core.gateway.AppointmentGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class CreateReceivingDocument {
    private final AppointmentGateway appointmentGateway;
    private final TemplateEngine templateEngine;

    public byte[] execute(final String appointmentId) {
        final AppointmentDomain appointmentDomain = appointmentGateway.findById(appointmentId);

        List<ItemDomain> items = appointmentDomain.getInboundsDomain().stream()
                .flatMap(inbound -> inbound.getItems().stream())
                .map(item -> new ItemDomain(null, item.getQuantity(), item.getDescription(), null))
                .toList();

        try {
            Context context = new Context();
            context.setVariable("sellerName", appointmentDomain.getSeller().getName());
            context.setVariable("sellerCnpj", appointmentDomain.getSeller().getCnpj());
            context.setVariable("items", items);

            String html = templateEngine.process("receiving-document", context);

            ByteArrayOutputStream pdfOutput = new ByteArrayOutputStream();
            HtmlConverter.convertToPdf(
                    new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8)),
                    pdfOutput
            );

            return pdfOutput.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}