package com.invoice.service.impl;

import com.invoice.dto.exception.InvoiceNotFoundException;
import com.invoice.entity.InvoiceEntity;
import com.invoice.entity.InvoiceItemEntity;
import com.invoice.repository.InvoiceItemRepository;
import com.invoice.repository.InvoiceRepository;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.Mock;
import static org.mockito.Mockito.*;


import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InvoiceServiceImplTest {

	@InjectMocks
	@Autowired
	private InvoiceServiceImpl service;

	@MockBean
	private InvoiceRepository invoiceRepository;

	@MockBean
	private InvoiceItemRepository invoiceItemRepository;


	@Test
	public void get_sendExistId_MustReturnResult_SuccessTest() {
		//Arrange
		Long id = Long.valueOf(1l);
		var optional = this.getMockOptionalInvoiceEntity(id);
		when(this.invoiceRepository.findById(id)).thenReturn(optional);
		when(this.invoiceItemRepository.findByInvoiceOrderByIdDesc(optional.get())).thenReturn(this.geMockInvoiceItemEntitys(id));

		//Act
		var invoiceDto = this.service.get(id);

		//Assert
		assertNotNull(invoiceDto);
		assertNotNull(invoiceDto.getInvoiceItems());
		assertEquals("joao", invoiceDto.getName());
	}

	@Test(expected = InvoiceNotFoundException.class)
	public void get_sendNonExistId_MustThrow_FailTest() {
		//Arrange
		Long id = Long.valueOf(1l);
		var optional = this.getMockEmptyOptionalInvoiceEntity(id);
		when(this.invoiceRepository.findById(id)).thenReturn(optional);

		//Act
		this.service.get(id);

		//Assert
	}

	private Optional<InvoiceEntity> getMockOptionalInvoiceEntity(Long id) {
		Optional<InvoiceEntity> optional = Optional.ofNullable(geMockInvoiceEntity(id));
		return optional;
	}

	private Optional<InvoiceEntity> getMockEmptyOptionalInvoiceEntity(Long id) {
		Optional<InvoiceEntity> optional = Optional.ofNullable(null);
		return optional;
	}

	private InvoiceEntity geMockInvoiceEntity(Long id) {
		var invoiceEntity = new InvoiceEntity();
		invoiceEntity.setTotalAmount(new BigDecimal(220));
		invoiceEntity.setTotalAmount(new BigDecimal(100));
		invoiceEntity.setId(id);
		invoiceEntity.setName("joao");
		invoiceEntity.setTotalShippingAmount(new BigDecimal(20));
		return invoiceEntity;
	}

	private List<InvoiceItemEntity> geMockInvoiceItemEntitys(Long id) {
		var invoiceEntity = this.geMockInvoiceEntity(id);

		List<InvoiceItemEntity> list = new ArrayList<>();

		var invoiceItemEntity1 = new InvoiceItemEntity();
		invoiceItemEntity1.setInvoice(invoiceEntity);
		invoiceItemEntity1.setId(1l);
		invoiceItemEntity1.setProductDescription("arroz");
		list.add(invoiceItemEntity1);

		var invoiceItemEntity2 = new InvoiceItemEntity();
		invoiceItemEntity2.setInvoice(invoiceEntity);
		invoiceItemEntity2.setId(2l);
		invoiceItemEntity2.setProductDescription("massa");
		list.add(invoiceItemEntity2);

		return list;
	}

}