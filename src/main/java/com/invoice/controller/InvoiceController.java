package com.invoice.controller;

import com.invoice.dto.invoice.InvoiceDto;
import com.invoice.dto.invoice.create.CreateInvoiceDto;
import org.springframework.web.bind.annotation.*;
import com.invoice.service.InvoiceService;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/invoices")

public class InvoiceController {

	private final InvoiceService invoiceService;

	public InvoiceController(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	@ApiOperation("List all the invoices")
	@GetMapping("/")
	public List<InvoiceDto> all() {
		return this.invoiceService.list();
	}

	@ApiOperation("Return the invoice by id")
	@GetMapping("/{id}")
	public InvoiceDto get(@PathVariable Long id) {
		return this.invoiceService.get(id);
	}

	@ApiOperation("Create an invoice")
	@PostMapping
	public InvoiceDto create(@Valid @RequestBody CreateInvoiceDto notaFiscalDto) {
		return this.invoiceService.create(notaFiscalDto);
	}

}
