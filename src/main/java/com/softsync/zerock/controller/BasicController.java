package com.softsync.zerock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.softsync.zerock.entity.Item;
import com.softsync.zerock.entity.ProcurementPlan;
import com.softsync.zerock.entity.ProductionPlan;
import com.softsync.zerock.entity.User;
import com.softsync.zerock.repository.ItemRepository;
import com.softsync.zerock.repository.ProcurementPlanRepository;
import com.softsync.zerock.repository.ProductionPlanRepository;
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
	
	@Autowired
	ProductionPlanRepository productionPlanRepository;
	@Autowired
	ProcurementPlanRepository procurementPlanRepository;

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
	    return "/procurement/add_prod";
	}
	
	@GetMapping("/add_LTplan")
	public String addLTplanview(
	        Model model,
	        @RequestParam(value = "pageProduction", required = false, defaultValue = "0") int pageProduction,
	        @RequestParam(value = "pageProcurement", required = false, defaultValue = "0") int pageProcurement,
	        @RequestParam(value = "searchField", required = false) String searchField,
	        @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
	        @RequestParam(value = "startDate", required = false) String startDate,
	        @RequestParam(value = "endDate", required = false) String endDate){
	    
	    Pageable pageableProduction = PageRequest.of(pageProduction, 10);
	    Page<ProductionPlan> productionPlans;
	    
	    if (searchField != null && searchKeyword != null && !searchKeyword.isEmpty()) {
	        try {
	            switch (searchField) {
	                case "itemCode":
	                    productionPlans = productionPlanRepository.findByItem_ItemCodeContaining(searchKeyword, pageableProduction);
	                    break;
	                case "itemName":
	                    productionPlans = productionPlanRepository.findByItem_ItemNameContaining(searchKeyword, pageableProduction);
	                    break;
	                case "productionStartDate":
	                    productionPlans = productionPlanRepository.findByProductionStartDateContaining(searchKeyword, pageableProduction);
	                    break;
	                case "productionEndDate":
	                    productionPlans = productionPlanRepository.findByProductionEndDateContaining(searchKeyword, pageableProduction);
	                    break;
	                case "productCode":
	                    productionPlans = productionPlanRepository.findByProductCodeContaining(searchKeyword, pageableProduction);
	                    break;
	                case "productName":
	                    productionPlans = productionPlanRepository.findByProductNameContaining(searchKeyword, pageableProduction);
	                    break;
	                case "productionQuantity":
	                    productionPlans = productionPlanRepository.findByProductionQuantity(Integer.parseInt(searchKeyword), pageableProduction);
	                    break;
	                case "itemQuantity":
	                    productionPlans = productionPlanRepository.findByItemQuantity(Integer.parseInt(searchKeyword), pageableProduction);
	                    break;
	                default:
	                    productionPlans = productionPlanRepository.findAll(pageableProduction);
	                    break;
	            }
	        } catch (Exception e) {
	            // 예외가 발생한 경우 전체 목록을 반환하거나 적절한 오류 처리를 합니다.
	            productionPlans = productionPlanRepository.findAll(pageableProduction);
	        }
	    } else {
	        productionPlans = productionPlanRepository.findAll(pageableProduction);
	    }
	    
	    Pageable pageableProcurement = PageRequest.of(pageProcurement, 10);
	    Page<ProcurementPlan> procurementPlans = procurementPlanRepository.findAll(pageableProcurement);

	    model.addAttribute("productionPlans", productionPlans);
	    model.addAttribute("procurementPlans", procurementPlans);
	    model.addAttribute("searchField", searchField);
	    model.addAttribute("searchKeyword", searchKeyword);
	    model.addAttribute("startDate", startDate);
	    model.addAttribute("endDate", endDate);
	    return "/procurement/add_LTplan";
	}
	
	
	//계약 
//	@GetMapping("/add_contract")   
//	public String addcontractview(Model model) {
//		
////		  List<Item> items = itemRepository.findAll();
////	       model.addAttribute("items", items);
//		
//		return"/procurement/add_contract";
//	}
//	
	
	
	@GetMapping("/inventory_period")
	public String inventoryview() {
		return"/materials/inventory_period";
	}
	
	@GetMapping("/invoice")
	public String invoiceview() {
		return"/materials/invoice";
	}
	

//	@GetMapping("/receivings")
//	public String receivingview() {
//		return"/materials/receivings";
//	}
	
	@GetMapping("/stock_report")
	public String stockReport() {
		return"/materials/stock_report";
	}
	@GetMapping("/shipping_list")
	public String shippinglistview() {
		return"/materials/shipping_list";
	}
	
	@GetMapping("/purchase_schedule")
	public String purchaseview() {
		return"/orders/purchase_schedule";
	}
	@GetMapping("/dashBoard")
	public String dashBoard() {
		return"/common/home";
	}

}
