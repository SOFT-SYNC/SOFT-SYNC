package com.softsync.zerock.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
            startDate = LocalDate.now().minusMonths(1); // 기본값: 1개월 전
        }
        if (endDate == null) {
            endDate = LocalDate.now(); // 기본값: 오늘
        }
        List<InventoryPeriodSummaryDTO> inventoryPeriods = inventoryPeriodService.getInventoryPeriodSummaries(startDate, endDate);
        model.addAttribute("inventoryPeriods", inventoryPeriods);
        return "/materials/inventory_period";
    }
}
