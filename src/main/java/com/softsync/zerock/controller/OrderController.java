package com.softsync.zerock.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softsync.zerock.entity.Company;
import com.softsync.zerock.entity.Contract;
import com.softsync.zerock.entity.Inspection;
import com.softsync.zerock.entity.Item;
import com.softsync.zerock.entity.Orders;
import com.softsync.zerock.repository.ContractRepository;
import com.softsync.zerock.repository.ItemRepository;
import com.softsync.zerock.repository.OrderRepository;
import com.softsync.zerock.service.CompanyService;
import com.softsync.zerock.service.ContractService;
import com.softsync.zerock.service.EmailService;
import com.softsync.zerock.service.InspectionService;
import com.softsync.zerock.service.ItemService;
import com.softsync.zerock.service.OrderService;
import com.softsync.zerock.service.ReceivingService;

import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {
   @Autowired
   OrderService orderService;

   @Autowired
   ContractService contractService;

   @Autowired
   CompanyService companyService;

   @Autowired
   ItemService itemService;
   
   @Autowired
   EmailService emailService;
   
   @Autowired
   InspectionService inspectionService;
   
   @Autowired
   OrderRepository orderRepository;

	@Autowired
	ItemRepository itemRepository;
	
	@Autowired
	ContractRepository contractRepository;
	
   @Autowired //발주 - 입고 연계를 위해 추가 5/23 김홍택
   ReceivingService receivingService;
   
   
   
   @GetMapping("/purchase_order")
   public String purchaseOrder(Model model,
              @RequestParam(value = "page", required = false, defaultValue = "0") int page,
              @RequestParam(value = "searchField", required = false) String searchField,
              @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
              HttpSession session) {
       
       System.out.println("[OrderController] getPurchaseOrder()");
       
       Pageable pageable = PageRequest.of(page, 10);
       
       Page<Contract> contracts;  // contracts 변수를 초기화
       boolean contractSearchEmpty = false;
          
       List<Orders> orderList = orderService.getAllOrders();
       model.addAttribute("orders", orderList); 

       // 세션에서 검색 조건을 초기화하거나 세션에 저장합니다.
       if (searchField != null && searchKeyword != null && !searchKeyword.isEmpty()) {
           session.setAttribute("contractSearchField", searchField);
           session.setAttribute("contractSearchKeyword", searchKeyword);
       } else {
           session.removeAttribute("contractSearchField");
           session.removeAttribute("contractSearchKeyword");
       }

       if (searchField != null && searchKeyword != null && !searchKeyword.isEmpty()) {
           try {
               switch (searchField) {
                  
                   case "itemCode":
                       contracts = contractRepository.findByItem_ItemCodeContaining(searchKeyword, pageable);
                       break;
                   case "itemName":
                       contracts = contractRepository.findByItem_ItemNameContaining(searchKeyword, pageable);
                       break;
                   default:
                       contracts = contractRepository.findAll(pageable);
                       break;
               }
           } catch (Exception e) {
               contracts = contractRepository.findAll(pageable);
           }
       } else {
           contracts = contractRepository.findAll(pageable);
       }

       if (contracts.isEmpty()) {
           contractSearchEmpty = true;
           contracts = contractRepository.findAll(pageable); // 전체 목록 반환
       }

       model.addAttribute("contracts", contracts);
       model.addAttribute("searchField", searchField);
       model.addAttribute("searchKeyword", searchKeyword);
       model.addAttribute("contractSearchEmpty", contractSearchEmpty);

       return "/orders/purchase_order";
   }

		

	@PostMapping("/getContractInfo")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getContractInfo(@RequestBody Map<String, String> request) {
		System.out.println("[OrderController] getContractInfo()");
		String itemCode = request.get("itemCode");

		Contract contract = contractService.getContractByItemCode(itemCode);
		Company company = companyService.getCompanyByContract(contract);

		Map<String, Object> response = new HashMap<>();
		response.put("contract", contract);
		response.put("company", company);

		return ResponseEntity.ok(response);
	}

	
	//발주서 저장? 
	@PostMapping("/saveOrders")
	public String saveOrders(
	    @RequestParam("brn") String brn, 
	    @RequestParam("contract_number") int contractNumber,
	    @RequestParam("company_name") String companyName, 
	    @RequestParam("company_ceo") String companyCeo,
	    @RequestParam("company_address") String companyAddress, 
	    @RequestParam("manager") String manager,
	    @RequestParam("manager_tel") String managerTel, 
	    @RequestParam("itemCode") String itemCode,
	    @RequestParam("itemName") String itemName, 
	    @RequestParam("material") String material,
	    @RequestParam("dimensions") String dimensions, 
	    @RequestParam("orderQuantity") int orderQuantity,
	    @RequestParam("unitPrice") int unitPrice, 
	    @RequestParam("orderNote") String orderNote,
	    @RequestParam("orderDate") String orderDate,
	    @RequestParam("receiveDuedate") String receiveDuedate,
	    @RequestParam("totalPrice") String totalPrice,
	    Model model) {

	    System.out.println("[OrderController] saveOrders()");
	    
	    Orders order = new Orders(); 
	    Company company = orderService.getorderByBrn(brn);
	    Item item = orderService.getItemByItemCode(itemCode);
	    Contract contract = contractService.getContractByItemCode(itemCode);
	    String orderNo = orderService.generateOrderNo(); // 발주번호 자동

	    order.setContract(contract);
	    order.setCompany(company);
	    order.setItem(item); 
	    order.setOrderNo(orderNo);
	    order.setInspectionTime("0/0");

	    // orderDate 문자열을 LocalDate로 변환
	    LocalDate parsedOrderDate = LocalDate.parse(orderDate);
	    order.setOrderDate(parsedOrderDate);

	    // receiveDuedate 문자열을 LocalDate로 변환
	    LocalDate parsedReceiveDuedate = LocalDate.parse(receiveDuedate);
	    order.setReceiveDuedate(Date.valueOf(parsedReceiveDuedate));

	    order.setOrderQuantity(orderQuantity);
	    order.setOrderNote(orderNote);
	    order.setTotalPrice(totalPrice);
	    order.setOrderYn("Y"); // '저장' 버튼을 눌렀을 때 orderYn을 'Y'로 설정
	    order.setReceiptYn("Y");

	    orderService.saveOrder(order);
	    
	    
	  //발주 - 입고 연계를 위해 추가 5/23 김홍택
	    if(order.getOrderYn().equals("Y")){
	    	receivingService.saveReceiving(order);
	    }
	    return "redirect:/purchase_order";
	}

	@PostMapping("/pushOrders")
	public String pushOrders() {
		System.out.println("[OrderController] pushOrders()");
		
		  Orders order = new Orders(); 
		  
		  
		order.setReceiptYn("Y"); // '발주서발행' 버튼을 눌렀을 때 발주서 발행여부 'Y'로 설정
		
		  return "redirect:/purchase_order";
	}
    
 
	@PostMapping("/getOrderDetails")
	@ResponseBody
	public ResponseEntity<Orders> getOrderDetails(@RequestBody Map<String, String> request) {
	    System.out.println("[OrderController] getOrdersInfo()");
	    String orderNo = request.get("orderNo");

	    Orders orderDetails = orderService.getOrderDetailsByOrderNo(orderNo);
	    
	    // 필요한 데이터를 로그에 출력
	    System.out.println("Order Details: " + orderDetails);

	    return ResponseEntity.ok(orderDetails);
	}

	@PostMapping("/sendOrderEmail")
    public String sendOrderEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String text) {
        try {
            emailService.sendEmail(to, subject, text);
            return "Email sent successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send email.";
        }
    }
	
	
	 @GetMapping("/purchase_order_list")
	    public String purchaseOrderListView(Model model,
	              @RequestParam(value = "page", required = false, defaultValue = "0") int page,
	              @RequestParam(value = "searchField", required = false) String searchField,
	              @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
	              HttpSession session) {
		 
		 System.out.println("[OrderContorller] getOrderedList()");
			
		 
		  Pageable pageable = PageRequest.of(page, 10);
	       
	       Page<Contract> contracts;  // contracts 변수를 초기화
	       boolean contractSearchEmpty = false;
	          
	       List<Orders> orderList = orderService.getAllOrders();
	       model.addAttribute("orders", orderList); 

	       // 세션에서 검색 조건을 초기화하거나 세션에 저장합니다.
	       if (searchField != null && searchKeyword != null && !searchKeyword.isEmpty()) {
	           session.setAttribute("contractSearchField", searchField);
	           session.setAttribute("contractSearchKeyword", searchKeyword);
	       } else {
	           session.removeAttribute("contractSearchField");
	           session.removeAttribute("contractSearchKeyword");
	       }

	       if (searchField != null && searchKeyword != null && !searchKeyword.isEmpty()) {
	           try {
	               switch (searchField) {
	                  
	                   case "itemCode":
	                       contracts = contractRepository.findByItem_ItemCodeContaining(searchKeyword, pageable);
	                       break;
	                   case "itemName":
	                       contracts = contractRepository.findByItem_ItemNameContaining(searchKeyword, pageable);
	                       break;
	                   case "orderNo":
	                       contracts = contractRepository.findByOrders_OrderNoContaining(searchKeyword, pageable);
	                       break;
	                   default:
	                       contracts = contractRepository.findAll(pageable);
	                       break;
	               }
	           } catch (Exception e) {
	               contracts = contractRepository.findAll(pageable);
	           }
	       } else {
	           contracts = contractRepository.findAll(pageable);
	       }

	       if (contracts.isEmpty()) {
	           contractSearchEmpty = true;
	           contracts = contractRepository.findAll(pageable); // 전체 목록 반환
	       }

	       model.addAttribute("contracts", contracts);
	       model.addAttribute("searchField", searchField);
	       model.addAttribute("searchKeyword", searchKeyword);
	       model.addAttribute("contractSearchEmpty", contractSearchEmpty);

	        return "/orders/purchase_order_list";
	    }

	
	 @GetMapping("/purchase_schedule")
		public String purchaseview(Model model) {
			 System.out.println("[OrderContorller] getinspecList()");

			    List<Contract> contracts = orderService.getAllContracts();  
			    model.addAttribute("contracts", contracts);
			    
			    List<Orders> orderList = orderService.getAllOrders();  
			    model.addAttribute("orders", orderList); 
			    

//			    List<Inspection> inspections = inspectionService.getAllInspections();
//			    model.addAttribute("inspections", inspections);
//			    
//			    List<InspectionList> inspectionList = inspectionListService.getAllInspectionList();
//			    model.addAttribute("inspectionList", inspectionList);
//			    
//			    System.out.println("[OrderController] Inspections:");
//			    for (Inspection inspection : inspections) {
//			        System.out.println(inspection.toString());
//			    }
			   

			return"/orders/purchase_schedule";
		}	
		
	 
	 //검수 완료
	 @PostMapping("/saveInspection")
	 @ResponseBody
		public Map<String, Object> saveInspection(@RequestParam Long inspecNo,
				@RequestParam String percent,
				@RequestParam int quantity) {
		 
			System.out.println("[InspetionController] saveInspection()" + quantity);

			Inspection inspection = inspectionService.getinspectionByInspecNo(inspecNo);

			LocalDate inspecDate = LocalDate.now();
			

			
			
	        //검수마감 진행
			 int totalQuantity = 0;
			  Orders order = inspection.getOrders(); 
	        List<Inspection> inspections = inspectionService.getInspectionsByOrderNo(order.getOrderNo());//이거 아래 또 써야될듯
	        
		    for (Inspection inspection1 : inspections) {
		    	if(inspection1.getInspecYn() == 'Y') { //진척검수 완료된것만 더했을때
		    		totalQuantity += inspection1.getQuantity();
		    	}
		    }
		    System.out.println(totalQuantity);
	    	totalQuantity += quantity;
		    System.out.println("발주량 : " + order.getOrderQuantity() + "토탈 : " +totalQuantity);
		    //발주량 == 검수량(생산량) 이면 검수마감 버튼이 생기게 허여함~
	        if(order.getOrderQuantity() == totalQuantity) {
	        	order.setInspectYNG('G'); //검수완료 및 검수 마감 대기
	        }
	        
	      

	        
	        
			 
			inspection.setPercent(percent);
			System.out.println(percent);
			inspection.setInspecYn('Y');
			inspection.setInspecDate(inspecDate);
			
	
			
			inspectionService.save(inspection);

//			inspectionService.saveInspectionList(inspectionList);
		
			
			
			 //orders 안에 진척검수 완료량 갱신 :  검수완료 갯수 +1
		    String str = order.getInspectionTime();
		    String[] parts = str.split("/"); //문자열 나누기
	        int num = Integer.parseInt(parts[0]) + 1; //앞 숫자 1 추가
	        String newStr = num + "/" + parts[1]; //문자열 합치기
	        order.setInspectionTime(newStr); //저장시키기
	        orderService.saveOrder(order);  //db에 저장


	        order.setOrderQuantity(totalQuantity);
			Map<String, Object> response = new HashMap<>();
			response.put("inspectionList", inspections);
			
			return response;

		}

	 //발주 진행 현황 리포트
	@GetMapping("/purchase_order_tracking")
	public String orderTracking(Model model) {
		System.out.println("발주 컨트롤러 : 발주현황 그래프");
			
		Long arr[] = orderService.trackingCount();
		
		model.addAttribute("arr", arr);
			
		return "/orders/purchase_order_tracking";
	}
	
	@GetMapping("/seach")
	 public ResponseEntity<Long[]> search(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
		 System.out.println("발주 컨트롤러 : 기간 검색");
		 System.out.println(startDate);
		 Long arr[] = orderService.search(LocalDate.parse(startDate), LocalDate.parse(endDate));
	     
	        return  ResponseEntity.ok(arr);
	    }
}
