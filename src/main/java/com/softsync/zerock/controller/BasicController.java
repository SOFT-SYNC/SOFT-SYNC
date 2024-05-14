package com.softsync.zerock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.softsync.zerock.entity.Item;
import com.softsync.zerock.entity.User;
import com.softsync.zerock.repository.ItemRepository;
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
	ItemRepository itemRepository;
	
	@Autowired
	UploadFileService uploadFileService;

	@GetMapping("/login")
	public String loginview() {
		return "/common/login";
	}

	@GetMapping("/welcome")
	public String welcome() {
		return "/common/welcome";
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
	
	@GetMapping("/add_prod")
	   public String getItems(Model model, @PageableDefault(size = 10) Pageable pageable) {
	       Page<Item> items = itemRepository.findAll(pageable);
	       model.addAttribute("items", items);
	       return "procurement/add_prod";
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
	
	@GetMapping("/purchase_order_tracking")
	public String orderTracking() {
		return"/orders/purchase_order_tracking";
	}
	@GetMapping("/receivings")
	public String receivingview() {
		return"/materials/receivings";
	}
	
	@GetMapping("/stock_report")
	public String stockReport() {
		return"/materials/stock_report";
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
		return"/common/noticeList";
	}
	
}
