package com.br.invoice.service;

import java.util.List;

import com.br.invoice.dto.invoice.InvoiceDto;
import com.br.invoice.dto.invoice.create.CreateInvoiceDto;

public interface InvoiceService {

	List<InvoiceDto> list();

	InvoiceDto create(CreateInvoiceDto notaFiscalDto);

	InvoiceDto get(Long id);
}
