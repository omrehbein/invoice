package com.invoice.service;

import java.util.List;

import com.invoice.dto.invoice.InvoiceDto;
import com.invoice.dto.invoice.create.CreateInvoiceDto;

public interface InvoiceService {

	List<InvoiceDto> list();

	InvoiceDto create(CreateInvoiceDto notaFiscalDto);

	InvoiceDto get(Long id);
}
