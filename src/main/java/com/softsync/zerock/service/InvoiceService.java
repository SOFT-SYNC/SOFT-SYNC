package com.softsync.zerock.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softsync.zerock.entity.Invoice;
import com.softsync.zerock.entity.Orders;
import com.softsync.zerock.entity.Receiving;
import com.softsync.zerock.repository.InvoiceRepository;
import com.softsync.zerock.repository.OrderRepository;
import com.softsync.zerock.repository.ReceivingRepository;

@Service
public class InvoiceService {
	@Autowired
	InvoiceRepository invoiceRespository;
	
	@Autowired
	ReceivingRepository receivingRepository;
	
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	InvoiceRepository invoiceRepository;
	
	public List<Invoice> getInvoices(){
		return invoiceRepository.findAll();
	}

	//
	public Receiving getReceive(String orderNo) {
		System.out.println("거래명세서 서비스 : 명세서 상세보기!");
		Orders order = orderRepository.getReferenceById(orderNo);
	   System.out.println("서비스" + order);
	    return receivingRepository.findByOrders(order);
	
	}
	
	public void saveInvice(int no) {
		System.out.println("거래명세서 서비스 : 발행!");
		Optional<Receiving> optionalReceiving = receivingRepository.findById(no);
		if (optionalReceiving.isPresent()) {
		    Receiving receiving = optionalReceiving.get();
		    
		    Invoice invoice = invoiceRepository.findByReceiving(receiving);
		    
		    invoice.setPublishYn('Y');
		    invoice.setPublishDate(LocalDate.now());
		    System.out.println(invoice);
		    invoiceRepository.save(invoice);
		}
	}
}
