package com.softsync.zerock.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.softsync.zerock.entity.Company;
import com.softsync.zerock.entity.Contract;
import com.softsync.zerock.entity.Item;
import com.softsync.zerock.entity.Orders;
import com.softsync.zerock.repository.OrderRepository;
import com.softsync.zerock.service.OrderService;

@Controller
public class OrderController { 
	@Autowired
	OrderService orderService;

	@Autowired
	OrderRepository orderRepository;
	
	@GetMapping("/purchase_order")
	public String getPurchaseOrder(Model model) {
		System.out.println("[OrderContorller] getPurchaseOrder()");
		
		 List<Orders> orderList = orderService.getAllOrders();
		 List<Item> items = orderService.getAllItems();
		 model.addAttribute("items", items);
	       model.addAttribute("orderList", orderList);
		return "orders/purchase_order";
	}
	
	//발주 정보 저장
	@PostMapping ("/saveOrders")
    public String saveOrders(@RequestParam("brn")String brn, @RequestParam("company_name") String companyName, @RequestParam("company_ceo") String companyCeo,
    							@RequestParam("orderDate")Date orderDate, @RequestParam("company_address")String companyAdderss, @RequestParam("manager") String manager, @RequestParam("manager_tel") String managerTel,
				            Model model) {
		System.out.println("[OrderController] saveOrders()");
		Contract contract = new Contract();
        Company company = orderService.getorderByBrn(brn);
        Item item = orderService.getItemByItemCode(itemCode);
        
		return "redirect:purchase_order"; 
							
	}

	
	@GetMapping("/purchase_order_list")
	public String purchaseorederlistview() {
		return"/orders/purchase_order_list";
	}
	/*
	 * @GetMapping("/purchase_order") public String purchaseorderview() {
	 * return"/orders/purchase_order"; }
	 */
	
	@GetMapping("/purchase_order_tracking")
	public String orderTracking() {
		return"/orders/purchase_order_tracking";
	}

}
