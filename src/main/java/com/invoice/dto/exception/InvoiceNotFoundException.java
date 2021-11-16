package com.invoice.dto.exception;

public class InvoiceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private static final String MENSAGEM = "NF não encontrado";

	public InvoiceNotFoundException() {
		super(MENSAGEM);
	}

}