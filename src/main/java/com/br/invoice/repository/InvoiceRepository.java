package com.br.invoice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.invoice.entity.InvoiceEntity;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {

	List<InvoiceEntity> findAllByOrderByIdDesc();

}
