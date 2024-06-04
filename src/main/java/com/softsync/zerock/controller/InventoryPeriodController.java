package com.softsync.zerock.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softsync.zerock.DTO.InventoryPeriodSummaryDTO;
import com.softsync.zerock.service.InventoryPeriodService;

@Controller
public class InventoryPeriodController {

	@Autowired
	private InventoryPeriodService inventoryPeriodService;
 
	@GetMapping("/inventory_period")
	public String getInventoryPeriods(Model model,
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		if (startDate == null) {
            startDate = LocalDate.now().withMonth(1).withDayOfMonth(1); // 기본값: 당해년도 1월 1일
        }
        if (endDate == null) {
            endDate = LocalDate.now(); // 기본값: 오늘
        }
		List<InventoryPeriodSummaryDTO> inventoryPeriods = inventoryPeriodService.getInventoryPeriodSummaries(startDate,
				endDate);
		model.addAttribute("inventoryPeriods", inventoryPeriods);
		return "/materials/inventory_period";
	}

	 @GetMapping("/inventory_period_report")
	    @ResponseBody
	    public Map<String, Object> getInventoryPeriodReport(
	            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
	            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
	            @RequestParam(value = "limit", required = false, defaultValue = "6") int limit,
	            @RequestParam(value = "search", required = false, defaultValue = "") String search) {

	        System.out.println("Received startDate: " + startDate);
	        System.out.println("Received endDate: " + endDate);
	        System.out.println("Received search: " + search);

	        List<InventoryPeriodSummaryDTO> inventoryPeriods = inventoryPeriodService.getInventoryPeriodSummaries(startDate, endDate);

	        // 검색어가 포함된 품목만 필터링
	        if (!search.isEmpty()) {
	            inventoryPeriods = inventoryPeriods.stream()
	                    .filter(ip -> ip.getInventoryName().toLowerCase().contains(search.toLowerCase()))
	                    .collect(Collectors.toList());
	        }

	        // 상위 N개의 항목만 표시하도록 정렬 및 제한 (출고 기준)
	        inventoryPeriods = inventoryPeriods.stream()
	                .sorted((a, b) -> Long.compare(b.getTotalShipmentQuantity(), a.getTotalShipmentQuantity()))
	                .limit(limit)
	                .collect(Collectors.toList());

	        long totalReceivingQuantity = inventoryPeriods.stream()
	                .mapToLong(InventoryPeriodSummaryDTO::getTotalReceivingQuantity).sum();
	        long totalShipmentQuantity = inventoryPeriods.stream()
	                .mapToLong(InventoryPeriodSummaryDTO::getTotalShipmentQuantity).sum();
	        double totalInventoryValue = inventoryPeriods.stream()
	                .mapToDouble(InventoryPeriodSummaryDTO::getFinalInventoryValue).sum();

	        Map<String, Object> response = new HashMap<>();
	        response.put("inventoryPeriods", inventoryPeriods);
	        response.put("totalReceivingQuantity", totalReceivingQuantity);
	        response.put("totalShipmentQuantity", totalShipmentQuantity);
	        response.put("totalInventoryValue", totalInventoryValue);

	        return response;
	    }

	    @GetMapping("/default_inventory_period_report")
	    @ResponseBody
	    public Map<String, Object> getDefaultInventoryPeriodReport() {
	        
	        List<InventoryPeriodSummaryDTO> inventoryPeriods = inventoryPeriodService.getAllInventoryPeriodSummaries();

	        // 상위 6개의 항목만 표시하도록 정렬 및 제한 (출고 기준)
	        inventoryPeriods = inventoryPeriods.stream()
	                .sorted((a, b) -> Long.compare(b.getTotalShipmentQuantity(), a.getTotalShipmentQuantity()))
	                .limit(6)
	                .collect(Collectors.toList());

	        long totalReceivingQuantity = inventoryPeriods.stream()
	                .mapToLong(InventoryPeriodSummaryDTO::getTotalReceivingQuantity).sum();
	        long totalShipmentQuantity = inventoryPeriods.stream()
	                .mapToLong(InventoryPeriodSummaryDTO::getTotalShipmentQuantity).sum();
	        double totalInventoryValue = inventoryPeriods.stream()
	                .mapToDouble(InventoryPeriodSummaryDTO::getFinalInventoryValue).sum();

	        Map<String, Object> response = new HashMap<>();
	        response.put("inventoryPeriods", inventoryPeriods);
	        response.put("totalReceivingQuantity", totalReceivingQuantity);
	        response.put("totalShipmentQuantity", totalShipmentQuantity);
	        response.put("totalInventoryValue", totalInventoryValue);

	        return response;
	    }
}
