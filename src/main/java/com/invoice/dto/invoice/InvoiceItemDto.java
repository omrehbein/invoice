package com.invoice.dto.invoice;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class InvoiceItemDto {
    private Long id;
    private String productDescription;
    private BigDecimal itemAmount;
}