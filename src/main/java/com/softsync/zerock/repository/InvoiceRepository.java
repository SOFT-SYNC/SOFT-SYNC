package com.softsync.zerock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softsync.zerock.entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
	

}
