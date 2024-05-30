package com.softsync.zerock.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.softsync.zerock.entity.Invoice;
import com.softsync.zerock.entity.Receiving;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
	
	Invoice findByReceiving(Receiving receiving);
	
	Long countByPublishYn(char publishYn);
	
	
	@Query("SELECT COUNT(*) FROM Invoice WHERE publishDate BETWEEN :startDate AND :endDate")
	Long countByInvoiceDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
