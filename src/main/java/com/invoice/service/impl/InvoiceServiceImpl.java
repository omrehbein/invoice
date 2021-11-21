package com.invoice.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.invoice.dto.exception.InvoiceNotFoundException;
import com.invoice.dto.invoice.InvoiceDto;
import com.invoice.dto.invoice.InvoiceItemDto;
import com.invoice.dto.invoice.create.CreateInvoiceDto;
import com.invoice.dto.invoice.create.CreateInvoiceItemDto;
import com.invoice.entity.InvoiceItemEntity;
import com.invoice.repository.InvoiceItemRepository;
import com.invoice.entity.InvoiceEntity;
import com.invoice.service.InvoiceService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.invoice.repository.InvoiceRepository;

import javax.transaction.Transactional;

@Service
public class InvoiceServiceImpl implements InvoiceService {
	private final InvoiceRepository invoiceRepository;
	private final InvoiceItemRepository invoiceItemRepository;
	private final ModelMapper modelMapper;

	public InvoiceServiceImpl(InvoiceRepository invoiceRepository, InvoiceItemRepository invoiceItemRepository, ModelMapper modelMapper) {
		this.invoiceRepository = invoiceRepository;
		this.invoiceItemRepository = invoiceItemRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public List<InvoiceDto> list() {
		var invoices = this.invoiceRepository.findAllByOrderByIdDesc();
		return invoices.stream().map(this::mapToDto).collect(Collectors.toList());
	}

	@Override
	public InvoiceDto get(Long id) {
		var invoice = this.invoiceRepository.findById(id).orElseThrow(InvoiceNotFoundException::new);
		return this.mapToDto(invoice);
	}

	@Override
	@Transactional
	public InvoiceDto create(CreateInvoiceDto dto) {

		InvoiceEntity invoiceEntity = this.createInvoice(dto);
		var invoiceItemEntities = this.createInvoiceItems(dto.getInvoiceItems(), invoiceEntity);

		this.updateInvoiceAmounts(invoiceItemEntities, invoiceEntity);

		this.invoiceRepository.save(invoiceEntity);
		this.invoiceItemRepository.saveAll(invoiceItemEntities);

		return mapToDto(invoiceEntity, invoiceItemEntities);
	}

	private InvoiceEntity createInvoice(CreateInvoiceDto invoiceDto) {
		return this.modelMapper.map(invoiceDto, InvoiceEntity.class);
	}

	private List<InvoiceItemEntity> createInvoiceItems(List<CreateInvoiceItemDto> createInvoiceItemDtos, InvoiceEntity invoiceEntity) {
		return createInvoiceItemDtos.stream().map(createInvoiceItemDto -> {
			var invoiceItemEntity = this.modelMapper.map(createInvoiceItemDto, InvoiceItemEntity.class);
			invoiceItemEntity.setInvoice(invoiceEntity);
			return invoiceItemEntity;
		}).collect(Collectors.toList());
	}

	private void updateInvoiceAmounts(List<InvoiceItemEntity> invoiceItemEntities, InvoiceEntity invoiceEntity) {
		var totalItemsAmount = invoiceItemEntities.stream().map(InvoiceItemEntity::getItemAmount).collect(Collectors.toList()).stream().reduce(BigDecimal.ZERO, BigDecimal::add);
		invoiceEntity.setTotalItemsAmount(totalItemsAmount);
		invoiceEntity.setTotalAmount(totalItemsAmount.add(invoiceEntity.getTotalShippingAmount()));
	}

	private InvoiceDto mapToDto(InvoiceEntity invoiceEntity) {
		var invoiceDto = this.modelMapper.map(invoiceEntity, InvoiceDto.class);
		var invoiceItemEntities = this.invoiceItemRepository.findByInvoiceOrderByIdDesc(invoiceEntity);
		this.setInvoiceItems(invoiceDto, invoiceItemEntities);
		return invoiceDto;
	}

	private InvoiceDto mapToDto(InvoiceEntity invoiceEntity, List<InvoiceItemEntity> invoiceItemEntities) {
		var invoiceDto = this.modelMapper.map(invoiceEntity, InvoiceDto.class);
		this.setInvoiceItems(invoiceDto, invoiceItemEntities);
		return invoiceDto;
	}

	private void setInvoiceItems(InvoiceDto invoiceDto, List<InvoiceItemEntity> invoiceItemEntities) {
		invoiceDto.setInvoiceItems(
			invoiceItemEntities.stream().map(invoiceItemEntity -> this.modelMapper.map(invoiceItemEntity, InvoiceItemDto.class)).collect(Collectors.toList())
		);
	}

}