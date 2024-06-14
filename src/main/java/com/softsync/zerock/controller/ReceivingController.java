package com.softsync.zerock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.softsync.zerock.entity.ReceiveList;
import com.softsync.zerock.entity.Receiving;
import com.softsync.zerock.repository.ReceiveListRepository;
import com.softsync.zerock.repository.ReceivingRepository;
import com.softsync.zerock.service.ReceivingService;

@Controller
public class ReceivingController {
	
	@Autowired
	ReceivingService receivingService;
	
	@Autowired
	ReceiveListRepository receiveListRepository;
 
	@GetMapping("/receivings")
	public String receiving(Model model,
	                        @RequestParam(defaultValue = "0") int page1,
	                        @RequestParam(defaultValue = "0") int page2,
	                        @RequestParam(defaultValue = "5") int size1,
	                        @RequestParam(defaultValue = "5") int size2,
	                        @RequestParam(value = "searchField", required = false) String searchField,
	                        @RequestParam(value = "searchKeyword", required = false) String searchKeyword, 
							@RequestParam(value = "searchField1", required = false) String searchField1,
					        @RequestParam(value = "searchKeyword1", required = false) String searchKeyword1) {
	    System.out.println("입고 컨트롤러 : 리스트 조회" + searchField1 + searchKeyword1);

	    // 첫 번째 데이터에 대한 페이지네이션
	    Pageable pageable1 = PageRequest.of(page1, size1);
	    Page<Receiving> receivingsP = receivingService.getAllItem(pageable1);

	    if(searchField1 != null && searchKeyword1 != null && !searchKeyword1.isEmpty()) {
	    	receivingsP = receivingService.searchReceivings1(searchField1, searchKeyword1, pageable1);
	    } else {
	    	receivingsP = receivingService.getAllItem(pageable1);
	    }
	    
	    
	    
	    
	    // 두 번째 데이터에 대한 페이지네이션
	    Pageable pageable2 = PageRequest.of(page2, size2);
	    Page<ReceiveList> receiveListsP;
	    if (searchField != null && searchKeyword != null && !searchKeyword.isEmpty()) {
	        receiveListsP = receivingService.searchReceivings(searchField, searchKeyword, pageable2);
	    } else {
	        receiveListsP = receivingService.getAllItems(pageable2);
	    }

	    model.addAttribute("receivingsP", receivingsP); // 자재 입고
	    model.addAttribute("receiveListsP", receiveListsP); // 자재 입고내역
	    model.addAttribute("searchField", searchField); // 검색 필터
	    model.addAttribute("searchKeyword", searchKeyword); // 검색어

	    return "/materials/receivings";
	}
	
	//입고 저장
	@PostMapping("/saveReceiving")
	public String saveReceiving(@RequestParam("quantity") int quantity,
							    @RequestParam("receivingNumber") int num) {
		System.out.println("입고 컨트롤러 : 자재입고" +quantity);
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
