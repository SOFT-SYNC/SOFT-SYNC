package com.softsync.zerock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.softsync.zerock.entity.Contract;
import com.softsync.zerock.service.ReceivingService;

@Controller
public class ReceivingController {
	
	@Autowired
	ReceivingService receivingService;

	@GetMapping("/receivings")
	public String resiving(Model model) {
		System.out.println("입고 컨트롤러 : 리스트 조회");
		
		List<Contract> contract = receivingService.resiving();
		
		model.addAttribute("contracts", contract);
		return "/materials/receivings";
	}
}
