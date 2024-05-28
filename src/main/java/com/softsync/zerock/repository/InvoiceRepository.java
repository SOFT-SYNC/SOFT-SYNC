package com.softsync.zerock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softsync.zerock.entity.Invoice;
import com.softsync.zerock.entity.Receiving;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
	
	Invoice findByReceiving(Receiving receiving);

}
