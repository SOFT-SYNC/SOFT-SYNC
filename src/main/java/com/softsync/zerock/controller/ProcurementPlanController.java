package com.softsync.zerock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softsync.zerock.DTO.ProcurementPlanDTO;
import com.softsync.zerock.entity.ProcurementPlan;
import com.softsync.zerock.service.ProcurementPlanService;

@RestController
@RequestMapping("/api/procurement-plans")
public class ProcurementPlanController {
	@Autowired 
	private ProcurementPlanService procurementPlanService;
 
	@PostMapping
    public ResponseEntity<ProcurementPlan> createProcurementPlan(@RequestBody ProcurementPlanDTO procurementPlanDTO) {
        ProcurementPlan createdPlan = procurementPlanService.createProcurementPlan(procurementPlanDTO);
        return ResponseEntity.ok(createdPlan);
    }
}