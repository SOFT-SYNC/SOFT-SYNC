package com.softsync.zerock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.softsync.zerock.service.OrderService;

@Controller
public class OrderController { 
	@Autowired
	OrderService orderService;

	/*
	 * @GetMapping("/purchase_orderList") public String
	 * getPurchaseOrderList(@RequestParam("page") int page, Model model) {
	 * List<Order> orders = getOrderList(page);
	 * 
	 * model.addAttribute("purchaseOrder", orders);
	 * 
	 * return "orders/purchase_order_listAjax"; }
	 */

}
