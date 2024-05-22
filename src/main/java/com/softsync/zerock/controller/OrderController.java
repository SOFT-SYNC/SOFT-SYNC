package com.softsync.zerock.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
	  public String saveOrders( @RequestParam("brn")String brn,
					  			@RequestParam("company_name") String companyName,
					  			@RequestParam("company_ceo") String companyCeo,
					  			@RequestParam("company_address") String companyAddress,
					  			@RequestParam("manager") String manager,
					  			@RequestParam("manager_tel") String managerTel,
					  			@RequestParam("itemCode") String itemCode,
					  			@RequestParam("itemName") String itemName,
					  			@RequestParam("material") String material,
					  			@RequestParam("dimensions") String demensions,
					  			@RequestParam("orderQuantity") Integer orderQuantity,
					  			@RequestParam("unit_price") int unitPrice, 
					  			@RequestParam("orderNote") String orderNote,
					  			@RequestParam("orderDate") LocalDate orderDate,
					  			@RequestParam("receiveDuedate") Date receiveDuedate,
					  			Model model,
					  			@PageableDefault(size = 10) Pageable pageable) {
		  
	  System.out.println("[OrderController] saveOrders()");
	  

      Orders order = new Orders();
      Company company = orderService.getorderByBrn(brn);
      Item item = orderService.getItemByItemCode(itemCode);
      Contract contract = contractService.getContractByItemCode(itemCode);
      String orderNo = orderService.generateOrderNo(); //발주번호 자동

      order.setContract(contract);
      order.setCompany(company); 
      order.setItem(item);
      order.setOrderNo(orderNo);
      order.setOrderDate(orderDate);
      order.setReceiveDuedate(receiveDuedate);
      order.setOrderQuantity(orderQuantity);
      order.setOrderNote(orderNote);

      orderService.saveOrder(order);

      List<Item> items = orderService.getAllItems();
      model.addAttribute("items", items);

      return "redirect:/add_contract";
	  }
	 
	 
		
	@GetMapping("/purchase_order_list")
	public String purchaseorederlistview() {
		return "/orders/purchase_order_list";
	}

	@GetMapping("/purchase_order_tracking")
	public String orderTracking() {
		return "/orders/purchase_order_tracking";
	}

}
