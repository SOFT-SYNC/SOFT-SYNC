package com.softsync.zerock.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softsync.zerock.entity.Contract;
import com.softsync.zerock.entity.Item;
import com.softsync.zerock.entity.Notice;
import com.softsync.zerock.entity.Orders;
import com.softsync.zerock.entity.ProcurementPlan;
import com.softsync.zerock.entity.ProductionPlan;
import com.softsync.zerock.entity.User;
import com.softsync.zerock.repository.ContractRepository;
import com.softsync.zerock.repository.ItemRepository;
import com.softsync.zerock.repository.ProcurementPlanRepository;
import com.softsync.zerock.repository.ProductionPlanRepository;
import com.softsync.zerock.service.ItemService;
import com.softsync.zerock.service.NoticeService;
import com.softsync.zerock.service.OrderService;
import com.softsync.zerock.service.ReceivingService;
import com.softsync.zerock.service.ShipmentService;
import com.softsync.zerock.service.UploadFileService;
import com.softsync.zerock.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class BasicController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	ItemService itemService;
	
	@Autowired
	ItemRepository itemRepository;
	
	@Autowired
	NoticeService noticeService;
	
	@Autowired
	UploadFileService uploadFileService;
	
	@Autowired
	ContractRepository contractRepository;
	
	@Autowired
	ReceivingService receivingService;
	
	@Autowired
	ShipmentService shipmentService;

	@Autowired
	ProductionPlanRepository productionPlanRepository;
	@Autowired
	ProcurementPlanRepository procurementPlanRepository;
	 @Autowired
	   OrderService orderService;
	 
	@GetMapping("/login")
	public String loginview() {
		return "/common/login";
	}

	
	
	/*
	 * @GetMapping("/welcome") public String welcome() { return "/common/welcome"; }
	 */
	
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
	   public String getItems(Model model,
	           @RequestParam(value = "page", required = false, defaultValue = "0") int page,
	           @RequestParam(value = "searchField", required = false) String searchField,
	           @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
	           HttpSession session) {

	      Pageable pageable = PageRequest.of(page, 10);
	       Page<Item> items;
	       boolean itemSearchEmpty = false;

	       // 세션에서 검색 조건을 초기화하거나 세션에 저장합니다.
	       if (searchField != null && searchKeyword != null && !searchKeyword.isEmpty()) {
	           session.setAttribute("itemSearchField", searchField);
	           session.setAttribute("itemSearchKeyword", searchKeyword);
	       } else {
	           session.removeAttribute("itemSearchField");
	           session.removeAttribute("itemSearchKeyword");
	       }

	       if (searchField != null && searchKeyword != null && !searchKeyword.isEmpty()) {
	           try {
	               switch (searchField) {
	                   case "itemCode":
	                       items = itemRepository.findByItemCodeContaining(searchKeyword, pageable);
	                       break;
	                   case "itemName":
	                       items = itemRepository.findByItemNameContaining(searchKeyword, pageable);
	                       break;
	                   case "material":
	                       items = itemRepository.findByMaterialContaining(searchKeyword, pageable);
	                       break;
	                   default:
	                       items = itemRepository.findAll(pageable);
	                       break;
	               }
	           } catch (Exception e) {
	               items = itemRepository.findAll(pageable);
	           }
	       } else {
	           items = itemRepository.findAll(pageable);
	       }

	       if (items.isEmpty()) {
	           itemSearchEmpty = true;
	           items = itemRepository.findAll(pageable); // 전체 목록 반환
	       }

	       model.addAttribute("items", items);
	       model.addAttribute("searchField", searchField);
	       model.addAttribute("searchKeyword", searchKeyword);
	       model.addAttribute("itemSearchEmpty", itemSearchEmpty);
	       return "/procurement/add_prod";
	   }
	   

	
	 @GetMapping("/add_LTplan")
	   public String addLTplanview(
	           Model model,
	           @RequestParam(value = "pageProduction", required = false, defaultValue = "0") int pageProduction,
	           @RequestParam(value = "pageProcurement", required = false, defaultValue = "0") int pageProcurement,
	           @RequestParam(value = "searchField", required = false) String searchField,
	           @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
	           @RequestParam(value = "searchType", required = false) String searchType,
	           @RequestParam(value = "startDate", required = false) String startDate,
	           @RequestParam(value = "endDate", required = false) String endDate,
	           HttpSession session) {

	       Pageable pageableProduction = PageRequest.of(pageProduction, 10);
	       Page<ProductionPlan> productionPlans;
	       boolean productionSearchEmpty = false;

	       // 세션에서 검색 조건을 초기화하거나 세션에 저장합니다.
	       if ("production".equals(searchType) && searchField != null && searchKeyword != null && !searchKeyword.isEmpty()) {
	           session.setAttribute("productionSearchField", searchField);
	           session.setAttribute("productionSearchKeyword", searchKeyword);
	       	       
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
	               productionPlans = productionPlanRepository.findAll(pageableProduction);
	           }
	       } else {
	           productionPlans = productionPlanRepository.findAll(pageableProduction);
	       }

	       if (productionPlans.isEmpty()) {
	           productionSearchEmpty = true;
	           productionPlans = productionPlanRepository.findAll(pageableProduction); // 전체 목록 반환
	       }

	       Pageable pageableProcurement = PageRequest.of(pageProcurement, 10);
	       Page<ProcurementPlan> procurementPlans = procurementPlanRepository.findAll(pageableProcurement);
	       boolean procurementSearchEmpty = false;

	       if ("procurement".equals(searchType) && searchField != null && searchKeyword != null && !searchKeyword.isEmpty()) {
	           session.setAttribute("procurementSearchField", searchField);
	           session.setAttribute("procurementSearchKeyword", searchKeyword);
	       
	           try {
	               switch (searchField) {
	                   case "itemCode":
	                       procurementPlans = procurementPlanRepository.findByItem_ItemCodeContaining(searchKeyword, pageableProcurement);
	                       break;
	                   case "itemName":
	                       procurementPlans = procurementPlanRepository.findByItem_ItemNameContaining(searchKeyword, pageableProcurement);
	                       break;
	                   case "productionStartDate":
	                       procurementPlans = procurementPlanRepository.findByProductionPlan_ProductionStartDateContaining(searchKeyword, pageableProcurement);
	                       break;
	                   case "productionEndDate":
	                       procurementPlans = procurementPlanRepository.findByProductionPlan_ProductionEndDateContaining(searchKeyword, pageableProcurement);
	                       break;
	                   case "productCode":
	                       procurementPlans = procurementPlanRepository.findByProductionPlan_ProductCodeContaining(searchKeyword, pageableProcurement);
	                       break;
	                   case "productName":
	                       procurementPlans = procurementPlanRepository.findByProductionPlan_ProductNameContaining(searchKeyword, pageableProcurement);
	                       break;
	                   case "itemQuantity":
	                       procurementPlans = procurementPlanRepository.findByProductionPlan_ItemQuantity(Integer.parseInt(searchKeyword), pageableProcurement);
	                       break;
	                   case "productionQuantity":
	                       procurementPlans = procurementPlanRepository.findByProductionPlan_ProductionQuantity(Integer.parseInt(searchKeyword), pageableProcurement);
	                       break;
	                   case "procurementQuantity":
	                       procurementPlans = procurementPlanRepository.findByProcurementQuantity(Integer.parseInt(searchKeyword), pageableProcurement);
	                       break;
	                   case "procurementInterval":
	                       procurementPlans = procurementPlanRepository.findByProcurementInterval(Integer.parseInt(searchKeyword), pageableProcurement);
	                       break;
	                   case "procurementDueDate":
	                       procurementPlans = procurementPlanRepository.findByProcurementDueDateContaining(searchKeyword, pageableProcurement);
	                       break;
	                   default:
	                       procurementPlans = procurementPlanRepository.findAll(pageableProcurement);
	                       break;
	               }
	           } catch (Exception e) {
	               procurementPlans = procurementPlanRepository.findAll(pageableProcurement);
	           }
	       } else {
	           procurementPlans = procurementPlanRepository.findAll(pageableProcurement);
	       }

	       if (procurementPlans.isEmpty()) {
	           procurementSearchEmpty = true;
	           procurementPlans = procurementPlanRepository.findAll(pageableProcurement); // 전체 목록 반환
	       }

	       model.addAttribute("productionPlans", productionPlans);
	       model.addAttribute("procurementPlans", procurementPlans);
	       model.addAttribute("searchField", searchField);
	       model.addAttribute("searchKeyword", searchKeyword);
	       model.addAttribute("startDate", startDate);
	       model.addAttribute("endDate", endDate);
	       model.addAttribute("productionSearchEmpty", productionSearchEmpty);
	       model.addAttribute("procurementSearchEmpty", procurementSearchEmpty);
	       return "/procurement/add_LTplan";
	   }
	
	 
	 	//6/5 추가 조달관리에 납기일 받아오기위함
	 	@PostMapping("/add_lt_contrct")
	 	@ResponseBody
	   public Map<String, Integer> add_lt_contrct(@RequestBody Map<String, String> request) {
	 		String itemId = request.get("itemId"); //받아옴
	 		System.out.println(itemId);
	 		Long itemIdL = Long.parseLong(itemId);
	 		Item item = itemRepository.getReferenceById(itemIdL);
	 		System.out.println(item);
	 		Contract contract = contractRepository.findByItem(item);
	 		
	 		int ltTime = contract.getLead_time();

	 		Map<String, Integer> response = new HashMap<>();
	        response.put("ltTime", ltTime); 
	 		
	 		
	 		return response;
	 	}
	

	
	@GetMapping("/stock_report")
	public String stockReport() {
		return"/materials/stock_report";
	}

	@GetMapping("/dashBoard")
	public String dashBoard(Model model) {
	    System.out.println("[DashBoard]");

	 // 오늘 날짜
	    LocalDate today = LocalDate.now();

	    // 오늘 날짜에 해당하는 입고된 수량 계산
	    int receivingQuantity = receivingService.getReceivingQuantityByDate(today);

	    // 오늘 날짜에 해당하는 출고된 수량 계산
	    int shipmentQuantity = shipmentService.getShipmentQuantityByDate(today);
	    
	 // 오늘 날짜에 해당하는 발주된 수량 계산
	    int totalOrderQuantity = orderService.getTotalOrderQuantityByDate(today);

	    // 오늘 날짜에 해당하는 입고 예정 수량 계산
	    int totalReceiveDuedateQuantity = orderService.getTotalOrderQuantityByReceiveDuedate(today);


	    // 모델에 계산된 수량들 추가
	    model.addAttribute("receivingQuantity", receivingQuantity);
	    model.addAttribute("shipmentQuantity", shipmentQuantity);
	    model.addAttribute("totalOrderQuantity", totalOrderQuantity);
	    model.addAttribute("totalReceiveDuedateQuantity", totalReceiveDuedateQuantity);
	    
	    List<Contract> contracts = orderService.getAllContracts();
	    model.addAttribute("contracts", contracts);

	    List<Orders> orderList = orderService.getAllOrders();
	    model.addAttribute("orders", orderList);

	    List<Notice> notices = noticeService.getAllNotices();
	    model.addAttribute("notices", notices);

	    
	    return "/common/home";
	}
	
   

}
