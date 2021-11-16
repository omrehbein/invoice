package com.invoice.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "invoice_table")
public class InvoiceEntity {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "total_items_amount")
	private BigDecimal totalItemsAmount;

	@Column(name = "total_shipping_amount")
	private BigDecimal totalShippingAmount;

	@Column(name = "total_amount")
	private BigDecimal totalAmount;

	//@OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY)
	//private List<InvoiceItemEntity> invoiceItems;

}
