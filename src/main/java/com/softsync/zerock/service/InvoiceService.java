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
	    Optional<Orders> optionalOrder = orderRepository.findById(orderNo);
	    
	    if (optionalOrder.isPresent()) {
	        Orders order = optionalOrder.get();
	        // 주문을 사용하여 받기 정보를 찾습니다.
	        List<Receiving> res = receivingRepository.findByOrders(order);
	        
	        if (!res.isEmpty()) {
	            return res.get(0);
	        } else {
	            // 받기 정보가 없을 경우 예외 처리 또는 기본값 반환
	            throw new IllegalStateException("Receiving information not found for order: " + orderNo);
	        }
	    } else {
	        // 주문 정보가 없을 경우 예외 처리 또는 기본값 반환
	        throw new IllegalArgumentException("Order not found: " + orderNo);
	    }
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
