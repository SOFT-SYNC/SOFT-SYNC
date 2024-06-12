package com.softsync.zerock.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
                                     @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                     @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                     @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
       if (startDate == null) {
           startDate = LocalDate.now().withMonth(1).withDayOfMonth(1); // 기본값: 당해년도 1월 1일
       }
       if (endDate == null) {
           endDate = LocalDate.now(); // 기본값: 오늘
       }

       Pageable pageable = PageRequest.of(page, size);
       Page<InventoryPeriodSummaryDTO> inventoryPeriods = inventoryPeriodService.getInventoryPeriodSummaries(startDate, endDate, pageable);

       System.out.println("Total Pages: " + inventoryPeriods.getTotalPages());
       System.out.println("Current Page: " + inventoryPeriods.getNumber());
       System.out.println("Total Items: " + inventoryPeriods.getTotalElements());
       System.out.println("Page Size: " + inventoryPeriods.getSize());

       model.addAttribute("inventoryPeriods", inventoryPeriods.getContent());
       model.addAttribute("totalPages", inventoryPeriods.getTotalPages());
       model.addAttribute("currentPage", inventoryPeriods.getNumber());
       model.addAttribute("totalItems", inventoryPeriods.getTotalElements());
       model.addAttribute("pageSize", inventoryPeriods.getSize());
       model.addAttribute("startDate", startDate);
       model.addAttribute("endDate", endDate);

       return "/materials/inventory_period";
   }
   
   
   @GetMapping("/inventory_period_report")
   @ResponseBody
   public Map<String, Object> getInventoryPeriodReport(
           @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
           @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
           @RequestParam(value = "page", required = false, defaultValue = "0") int page,
           @RequestParam(value = "size", required = false, defaultValue = "10") int size,
           @RequestParam(value = "search", required = false, defaultValue = "") String search) {

       if (startDate == null) {
           startDate = LocalDate.now().withMonth(1).withDayOfMonth(1); // 기본값: 당해년도 1월 1일
       }
       if (endDate == null) {
           endDate = LocalDate.now(); // 기본값: 오늘
       }

       Pageable pageable = PageRequest.of(page, size);
       Page<InventoryPeriodSummaryDTO> inventoryPeriodsPage = inventoryPeriodService.getInventoryPeriodSummaries(startDate, endDate, pageable);

       List<InventoryPeriodSummaryDTO> inventoryPeriods = inventoryPeriodsPage.getContent();

       // 검색어가 포함된 품목만 필터링
       if (!search.isEmpty()) {
           inventoryPeriods = inventoryPeriods.stream()
                   .filter(ip -> ip.getInventoryName().toLowerCase().contains(search.toLowerCase()))
                   .collect(Collectors.toList());
       }

       // 상위 N개의 항목만 표시하도록 정렬 및 제한 (출고 기준)
       inventoryPeriods = inventoryPeriods.stream()
               .sorted((a, b) -> Long.compare(b.getTotalShipmentQuantity(), a.getTotalShipmentQuantity()))
               .limit(size)
               .collect(Collectors.toList());

       long finalInventory = inventoryPeriods.stream()
             .mapToLong(InventoryPeriodSummaryDTO::getFinalInventory).sum();
       double totalInventoryValue = inventoryPeriods.stream()
               .mapToDouble(InventoryPeriodSummaryDTO::getFinalInventoryValue).sum();

       Map<String, Object> response = new HashMap<>();
       response.put("inventoryPeriods", inventoryPeriods);
       response.put("finalInventory", finalInventory);
       response.put("totalInventoryValue", totalInventoryValue);
       response.put("totalPages", inventoryPeriodsPage.getTotalPages());
       response.put("currentPage", inventoryPeriodsPage.getNumber());
       response.put("totalItems", inventoryPeriodsPage.getTotalElements());
       response.put("pageSize", inventoryPeriodsPage.getSize());

       return response;
   }

   @GetMapping("/default_inventory_period_report")
   @ResponseBody
   public Map<String, Object> getDefaultInventoryPeriodReport(
           @RequestParam(value = "page", required = false, defaultValue = "0") int page,
           @RequestParam(value = "size", required = false, defaultValue = "10") int size) {

       Pageable pageable = PageRequest.of(page, size);
       Page<InventoryPeriodSummaryDTO> inventoryPeriodsPage = inventoryPeriodService.getAllInventoryPeriodSummaries(pageable);

       List<InventoryPeriodSummaryDTO> inventoryPeriods = inventoryPeriodsPage.getContent();

       // 상위 N개의 항목만 표시하도록 정렬 및 제한 (출고 기준)
       inventoryPeriods = inventoryPeriods.stream()
               .sorted((a, b) -> Long.compare(b.getTotalShipmentQuantity(), a.getTotalShipmentQuantity()))
               .limit(size)
               .collect(Collectors.toList());

       long finalInventory = inventoryPeriods.stream()
             .mapToLong(InventoryPeriodSummaryDTO::getFinalInventory).sum();
       double totalInventoryValue = inventoryPeriods.stream()
               .mapToDouble(InventoryPeriodSummaryDTO::getFinalInventoryValue).sum();

       Map<String, Object> response = new HashMap<>();
       response.put("inventoryPeriods", inventoryPeriods);
       response.put("finalInventory", finalInventory);
       response.put("totalInventoryValue", totalInventoryValue);
       response.put("totalPages", inventoryPeriodsPage.getTotalPages());
       response.put("currentPage", inventoryPeriodsPage.getNumber());
       response.put("totalItems", inventoryPeriodsPage.getTotalElements());
       response.put("pageSize", inventoryPeriodsPage.getSize());

       return response;
   }
}