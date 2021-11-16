package com.invoice.repository;

import com.invoice.entity.InvoiceEntity;
import com.invoice.entity.InvoiceItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItemEntity, Long> {


    List<InvoiceItemEntity> findByInvoiceOrderByIdDesc(InvoiceEntity invoiceEntity);
}
