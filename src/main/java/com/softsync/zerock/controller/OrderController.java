package com.softsync.zerock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
	public String getPurchaseOrder(Model model, @PageableDefault(size = 10) Pageable pageable) {
		 Page<Orders> orderList = orderRepository.findAll(pageable);
	       model.addAttribute("orderList", orderList);
		return "orders/purchase_order";
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
