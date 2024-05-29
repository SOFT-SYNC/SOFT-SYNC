package com.softsync.zerock.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.softsync.zerock.entity.Inspection;
import com.softsync.zerock.entity.InspectionList;
import com.softsync.zerock.entity.Orders;
import com.softsync.zerock.service.ContractService;
import com.softsync.zerock.service.InspectionListService;
import com.softsync.zerock.service.InspectionService;
import com.softsync.zerock.service.OrderService;

@RestController
public class InspectionController {

	@Autowired
	OrderService orderService;

	@Autowired
	ContractService contractService;

	@Autowired
	InspectionService inspectionService;

	@Autowired
	InspectionListService inspectionListService;

	 
	 
	@PostMapping("/api/inspections/create")
	public Inspection createInspection(@RequestParam String orderNo, @RequestParam LocalDate inspecPlan,
			@RequestParam Integer quantity, @RequestParam Integer times) {
		return inspectionService.createInspection(orderNo, inspecPlan, quantity, times);
	}

	@PostMapping("/api/inspections")
	public Map<String, Object> getInspectionsByOrderNo(@RequestBody Map<String, String> request) {
		String orderNo = request.get("orderNo");
		Orders order = orderService.getOrderDetails(orderNo);
		List<Inspection> inspections = inspectionService.getInspectionsByOrderNo(orderNo);

		Map<String, Object> response = new HashMap<>();
		response.put("order", order);
		response.put("inspectionList", inspections);

		return response;
	}


	/*
	 * @PostMapping("/api/inspections/save") public InspectionList
	 * saveInspectionDetails(@RequestParam Long inspecNo, @RequestParam LocalDate
	 * inspecDate,
	 * 
	 * @RequestParam Integer percent) { return
	 * inspectionService.completeInspection(inspecNo, inspecDate, percent); }
	 */
	/*
	 * 
	 * @PostMapping("/complete_inspection") public String
	 * completeInspection(@RequestParam Integer inspecNo,
	 * 
	 * @RequestParam LocalDate inspecDate,
	 * 
	 * @RequestParam Integer percent,
	 * 
	 * @RequestParam String inspecYn) {
	 * System.out.println("[InspectionController] completeInspection()");
	 * 
	 * inspectionService.completeInspection(inspecNo, inspecDate, percent,
	 * inspecYn);
	 * 
	 * return "redirect:/purchase_schedule"; }
	 * 
	 */

}
