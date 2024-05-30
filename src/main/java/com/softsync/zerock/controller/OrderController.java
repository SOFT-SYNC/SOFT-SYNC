package com.softsync.zerock.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.softsync.zerock.entity.InspectionList;
import com.softsync.zerock.entity.Item;
import com.softsync.zerock.entity.Orders;
import com.softsync.zerock.service.CompanyService;
import com.softsync.zerock.service.ContractService;
import com.softsync.zerock.service.EmailService;
import com.softsync.zerock.service.InspectionListService;
import com.softsync.zerock.service.InspectionService;
import com.softsync.zerock.service.ItemService;
import com.softsync.zerock.service.OrderService;
import com.softsync.zerock.service.ReceivingService;

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
   InspectionListService inspectionListService;
   
   @Autowired //발주 - 입고 연계를 위해 추가 5/23 김홍택
   ReceivingService receivingService;

	@GetMapping("/purchase_order")
	public String purchaseOrder(Model model) {
	    System.out.println("[OrderContorller] getPurchaseOrder()");

	    List<Contract> contracts = orderService.getAllContracts();
	    model.addAttribute("contracts", contracts);
	    
	    List<Orders> orderList = orderService.getAllOrders();
	    model.addAttribute("orders", orderList); 
	    

	    return "orders/purchase_order";
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
	    @RequestParam("totalPrice") int totalPrice,
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
	    public String purchaseOrderListView(Model model) {
		 System.out.println("[OrderContorller] getOrderedList()");
		   List<Contract> contracts = orderService.getAllContracts();
		    model.addAttribute("contracts", contracts);
		    
		    List<Orders> orderList = orderService.getAllOrders();
		    model.addAttribute("orders", orderList); 
		    
	        return "orders/purchase_order_list";
	    }

	
		
	 @GetMapping("/purchase_schedule")
		public String purchaseview(Model model) {
			 System.out.println("[OrderContorller] getinspecList()");

			    List<Contract> contracts = orderService.getAllContracts();
			    model.addAttribute("contracts", contracts);
			    
			    List<Orders> orderList = orderService.getAllOrders();
			    model.addAttribute("orders", orderList); 
			    
			    List<Inspection> inspections = inspectionService.getAllInspections();
			    model.addAttribute("inspections", inspections);
			    
			    List<InspectionList> inspectionList = inspectionListService.getAllInspectionList();
			    model.addAttribute("inspectionList", inspectionList);
			    System.out.println(inspectionList);
		
			return"/orders/purchase_schedule";
		}	
		
	 //검수 완료 
	 @PostMapping("/saveInspection")
		public String saveInspection(@RequestParam Long inspecNo,
				@RequestParam String percent) {

			System.out.println("[OrderController] saveInspection()");

			InspectionList inspectionList = new InspectionList();
			Inspection inspection = inspectionService.getinspectionByInspecNo(inspecNo);

			LocalDate inspecDate = LocalDate.now();
			
			inspectionList.setInspection(inspection);
			inspectionList.setPercent(percent);
			inspectionList.setInspecYn("Y");
			inspectionList.setInspecDate(inspecDate);

			inspectionService.saveInspectionList(inspectionList);

			return "redirect:/purchase_schedule";

		}

		

//발주현황 그래프"

	@GetMapping("/purchase_order_tracking")
	public String orderTracking(Model model) {
		System.out.println("발주 컨트롤러 : 발주현황 그래프\"");
			
		Long arr[] = orderService.trackingCount();
		
		model.addAttribute("arr", arr);
			
		return "/orders/purchase_order_tracking";
	}

}
