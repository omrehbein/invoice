package com.invoice.service.impl;

import com.invoice.dto.exception.InvoiceNotFoundException;
import com.invoice.dto.invoice.create.CreateInvoiceDto;
import com.invoice.dto.invoice.create.CreateInvoiceItemDto;
import com.invoice.entity.InvoiceEntity;
import com.invoice.entity.InvoiceItemEntity;
import com.invoice.repository.InvoiceItemRepository;
import com.invoice.repository.InvoiceRepository;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.internal.matchers.Any;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
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

	public InvoiceServiceImplTest() {
	}


	@Test
	public void get_sendExistId_MustReturnResult_SuccessTest() {

		//Arrange
		Long id = Long.valueOf(1l);
		var optional = this.getMockOptionalInvoiceEntity(id);
		when(this.invoiceRepository.findById(id)).thenReturn(optional);
		when(this.invoiceItemRepository.findByInvoiceOrderByIdDesc(optional.get())).thenReturn(this.geMockInvoiceItemEntities(id));

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
		//throw
	}

	@Test
	public void create_sendInvoiceDtoParam_MustReturnCorrectTotalAmount_SuccessTest() {
		//Arrange
		CreateInvoiceDto invoiceDto = this.getMockCreateInvoiceDto();

		//Act
		var invoiceCreatedDto = this.service.create(invoiceDto);

		//Assert
		assertEquals(BigDecimal.valueOf(213.0),invoiceCreatedDto.getTotalAmount());

	}

	private InvoiceEntity invoiceEntity;

	@Test
	public void create_sendInvoiceDtoParam_MustReturnCorrectTotalAmount_SuccessTest_JustForExample() {
		//Arrange
		CreateInvoiceDto invoiceDto = this.getMockCreateInvoiceDto();

		//** just for example: extract param from save
		when(this.invoiceRepository.save(any(InvoiceEntity.class))).thenAnswer(
				(Answer<InvoiceEntity>) invocation -> {
					invoiceEntity = invocation.getArgument(0);
					return invoiceEntity;
				}
		);

		//Act
		this.service.create(invoiceDto);

		//Assert
		//** just for example: extract param from save
		assertEquals(BigDecimal.valueOf(213.0),invoiceEntity.getTotalAmount());

	}

	@Test
	public void create_sendInvoiceDtoParam_MustSaveInvoiceAndItems_SuccessTest() {

		//Arrange
		CreateInvoiceDto invoiceDto = this.getMockCreateInvoiceDto();

		//Act
		this.service.create(invoiceDto);

		//Assert
		verify(this.invoiceRepository, times(1)).save(any(InvoiceEntity.class));
		verify(this.invoiceItemRepository, times(1)).saveAll(any());

	}

	private CreateInvoiceDto getMockCreateInvoiceDto() {
		CreateInvoiceDto invoiceDto = new CreateInvoiceDto();
		invoiceDto.setTotalShippingAmount(BigDecimal.valueOf(10.0));
		invoiceDto.setName("pessoa1");
		invoiceDto.setInvoiceItems(new ArrayList());
		invoiceDto.getInvoiceItems().add(getCreateInvoiceItemDto(101, "arroz"));
		invoiceDto.getInvoiceItems().add(getCreateInvoiceItemDto(102, "feijao"));
		return invoiceDto;
	}

	private CreateInvoiceItemDto getCreateInvoiceItemDto(double itemAmount, String productDescription) {
		CreateInvoiceItemDto invoiceItemDto = new CreateInvoiceItemDto();
		invoiceItemDto.setItemAmount(BigDecimal.valueOf(itemAmount));
		invoiceItemDto.setProductDescription(productDescription);
		return invoiceItemDto;
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

	private List<InvoiceItemEntity> geMockInvoiceItemEntities(Long id) {
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