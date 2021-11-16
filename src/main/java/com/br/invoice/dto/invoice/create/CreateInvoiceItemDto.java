package com.br.invoice.dto.invoice.create;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CreateInvoiceItemDto {
    private String productDescription;
    private BigDecimal itemAmount;
}