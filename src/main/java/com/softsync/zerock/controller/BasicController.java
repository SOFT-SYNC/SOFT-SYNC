package com.softsync.zerock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.softsync.zerock.User;
import com.softsync.zerock.DTO.ItemDto;
import com.softsync.zerock.service.ItemService;
import com.softsync.zerock.service.UploadFileService;
import com.softsync.zerock.service.UserService;

@Controller
public class BasicController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	ItemService itemService;
	
	@Autowired
	UploadFileService uploadFileService;

	@GetMapping("/login")
	public String loginview() {
		return "/common/login";
	}

	@GetMapping("/account_register")
	public String registerview(Model model) {
		// user 객체를 초기화
		User user = new User();

		// user 객체를 모델에 추가
		model.addAttribute("User", user);

		// "register" 템플릿을 반환하여 뷰를 렌더링

		return "/common/account_register";
	}
	
	@PostMapping("/account_register")
	public String registerUser(@ModelAttribute User user) {
		// 비밀번호를 암호화
		userService.registerUser(user);

		return "/common/login"; // 로그인 페이지로 리다이렉트
	}
	
	@GetMapping("/potal")
	public String mainview() {
		return "/common/potal";
	}
	
	//품목 등록,조회
	@GetMapping("/add_prod")
	public String addprodview(Model model, ItemDto itemDto) {
		System.out.println("[아이템 컨트롤러]품목 조회,등록창");
		
		  model.addAttribute("item", itemDto);
		  
		  //하단 품목 리스트 출력용
		  List<ItemDto> itemList = itemService.getAllItems();
	        model.addAttribute("itemList", itemList);
		return"/procurement/add_prod";
	}
	
	//품목 등록 조회 확인
	@PostMapping("/additem")
	public String additem(@RequestParam("file") MultipartFile file, @ModelAttribute("item") ItemDto item) {
		System.out.println("[아이템 컨트롤러]품목 신규등록 확인" + item);
		
		String nextPage = "redirect:/procurement/add_prod";


		
		if(file !=null) item.setBlueprint_org_name(file.getOriginalFilename()); //원래 파일명 저장
		String savedFileName = uploadFileService.upload(file);
		if(savedFileName != null) {
			item.setBlueprint_save_name(savedFileName); //저장 파일명 저장
			String item_code = item.getUnit_code() + item.getSub_code() + item.getPart_code() + 1; //코드생성(가라)
			item.setItem_code(item_code); //코드 주입
			 itemService.addItem(item);
		}
		
		return "redirect:/procurement/add_prod";
	}
	
	
	
	
	
	
	@GetMapping("/add_LTplan")
	public String addLTplanview() {
		return"/procurement/add_LTplan";
	}
	
	@GetMapping("/add_contract")
	public String addcontractview() {
		return"/procurement/add_contract";
	}
	
	@GetMapping("/inventory_period")
	public String inventoryview() {
		return"/materials/inventory_period";
	}
	
	@GetMapping("/invoice")
	public String invoiceview() {
		return"/materials/invoice";
	}
	
	@GetMapping("/purchase_order")
	public String purchaseorderview() {
		return"/orders/purchase_order";
	}
	
	@GetMapping("/receivings")
	public String receivingview() {
		return"/materials/receivings";
	}
	
	@GetMapping("/shipping_list")
	public String shippinglistview() {
		return"/materials/shipping_list";
	}
	@GetMapping("/purchase_order_list")
	public String purchaseorederlistview() {
		return"/orders/purchase_order_list";
	}
	@GetMapping("/purchase_schedule")
	public String purchaseview() {
		return"/orders/purchase_schedule";
	}
	@GetMapping("/dashBoard")
	public String dashBoard() {
		return"/common/home";
	}
	@GetMapping("/notice")
	public String notice() {
		return"/common/notice";
	}
	
}