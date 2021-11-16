package com.br.invoice.dto.invoice.create;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class CreateInvoiceDto {
	private String name;
	private BigDecimal totalShippingAmount;
	private List<CreateInvoiceItemDto> invoiceItems;
}