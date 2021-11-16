package com.invoice.dto.invoice;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class InvoiceDto {
	private Long id;
	private String name;
	private BigDecimal totalItemsAmount;
	private BigDecimal totalShippingAmount;
	private BigDecimal totalAmount;
	private List<InvoiceItemDto> invoiceItems;
}