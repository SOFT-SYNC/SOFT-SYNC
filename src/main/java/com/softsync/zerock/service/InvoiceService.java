package com.softsync.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softsync.zerock.entity.Receiving;
import com.softsync.zerock.repository.InvoiceRepository;
import com.softsync.zerock.repository.ReceivingRepository;

@Service
public class InvoiceService {
	
	@Autowired
	InvoiceRepository invoiceRepository;
	
	@Autowired
	ReceivingRepository receivingRepository;
	
	public List<Receiving> getReceivings(){
		return receivingRepository.findAll();
	}

}
