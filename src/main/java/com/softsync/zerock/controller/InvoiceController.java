package com.softsync.zerock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.softsync.zerock.entity.Receiving;
import com.softsync.zerock.service.InvoiceService;

@Controller
public class InvoiceController {
	
	@Autowired
	InvoiceService invoiceService;
	
	@GetMapping("/invoice")
	public String invoiceview(Model model) {
		System.out.println("거래명세서 컨트롤러 : 명세서 조회");
		
		List<Receiving> receivings = invoiceService.getReceivings();
		
		
		model.addAttribute("receivings", receivings);
		
		return"/materials/invoice";
	}

}
