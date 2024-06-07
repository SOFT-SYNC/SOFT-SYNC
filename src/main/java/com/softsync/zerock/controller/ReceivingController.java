package com.softsync.zerock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.softsync.zerock.entity.ReceiveList;
import com.softsync.zerock.entity.Receiving;
import com.softsync.zerock.service.ReceivingService;

@Controller
public class ReceivingController {
	
	@Autowired
	ReceivingService receivingService;
 
	@GetMapping("/receivings")
	public String receiving(Model model, @PageableDefault(size = 5) @Qualifier("page1") Pageable pageable,
			@PageableDefault(size = 5) @Qualifier("page2") Pageable pagere ) {
		System.out.println("입고 컨트롤러 : 리스트 조회");
		
		List<Receiving> receivings = receivingService.resiving();
		Page<Receiving> receivingsP = receivingService.getAllItem(pagere);
		
		List<ReceiveList> receiveLists = receivingService.resList();
		Page<ReceiveList> receiveListsP = receivingService.getAllItems(pageable);
		model.addAttribute("receivings", receivings);
		model.addAttribute("receiveLists", receiveLists);
		model.addAttribute("receiveListsP", receiveListsP); //페이지용
		model.addAttribute("receivingsP", receivingsP); //페이지용
		return "/materials/receivings";
	}
	
	//입고 저장
	@PostMapping("/saveReceiving")
	public String saveReceiving(@RequestParam("quantity") int quantity,
							    @RequestParam("receivingNumber") int num) {
		System.out.println("입고 컨트롤러 : 자재입고");
		receivingService.updateReceiving(quantity, num);
		
		return "redirect:/receivings";
	}
	
	//입고 마감
	@PostMapping("/endRecieving")
	public String endRecieving(@RequestParam("receivingNumber")int num) {
		System.out.println("입고 컨트롤러 : 자재 입고   마   감 ");
		
		receivingService.endRecieving(num);

		return "redirect:/receivings";
	}
	
	

}
