package com.softsync.zerock.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
import com.softsync.zerock.entity.Item;
import com.softsync.zerock.entity.Orders;
import com.softsync.zerock.service.CompanyService;
import com.softsync.zerock.service.ContractService;
import com.softsync.zerock.service.ItemService;
import com.softsync.zerock.service.OrderService;

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
	
	@GetMapping("/purchase_order")
	public String purchaseOrder(Model model) {
		System.out.println("[OrderContorller] getPurchaseOrder()");
		
		  List<Item> items = orderService.getAllItems();
	       model.addAttribute("items", items);
		
		return "orders/purchase_order";
	}
	
	@PostMapping("/getContractInfo")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getContractInfo(@RequestBody Map<String, String> request) {
        String itemCode = request.get("itemCode");
    
        Contract contract = contractService.getContractByItemCode(itemCode);
        Company company = companyService.getCompanyByContract(contract);

        Map<String, Object> response = new HashMap<>();
        response.put("contract", contract);
        response.put("company", company);

        return ResponseEntity.ok(response);
    }
	
    @GetMapping("/companies")
    public List<Company> getAllCompaniesForItemByItemCode(@RequestParam String itemCode) {
        return itemService.getAllCompaniesForItemByItemCode(itemCode);
    }


	
	 @PostMapping("/saveOrders")
	    public String saveOrders( @RequestParam("brn") String brn,
	                               @RequestParam("company_name") String companyName,
	                               @RequestParam("itemCode") String itemCode,
	                               @RequestParam("itemName") String itemName,
	                               @RequestParam("price") int price,
	                               Model model) {
			System.out.println("[OrderController] saveOrders()");
			
	        Orders order = new Orders();
	        Company company = orderService.getorderByBrn(brn);
	        Item item = orderService.getItemByItemCode(itemCode);
	    
	        
			  order.setCompany(company); 
			  order.setOrderDate(LocalDateTime.now());
			  order.setItem(item); 
			  order.setReceiveDuedate(null);
			  order.setContract(null); 
			  order.setOrderYn(null);//발주여부
			  
			  
			  List<Orders> orders = item.getOrders();
			  if (orders == null) { 
				  orders = new ArrayList<>(); 
				  }
			  orders.add(order); // new 발주정보 
			  item.setOrders(orders);
			  
			  // 아이템 정보 저장 
			  orderService.saveItem(item);
			 
	
	        List<Item> items = orderService.getAllItems();
		       model.addAttribute("items", items);
	
	        return "redirect:add_contract"; 
	    } 
		
	
	@GetMapping("/purchase_order_list")
	public String purchaseorederlistview() {
		return"/orders/purchase_order_list";
	}

	
	@GetMapping("/purchase_order_tracking")
	public String orderTracking() {
		return"/orders/purchase_order_tracking";
	}

}
