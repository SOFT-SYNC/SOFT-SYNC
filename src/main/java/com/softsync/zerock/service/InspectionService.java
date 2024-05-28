package com.softsync.zerock.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softsync.zerock.entity.Inspection;
import com.softsync.zerock.entity.Orders;
import com.softsync.zerock.repository.ContractRepository;
import com.softsync.zerock.repository.InspectionRepository;
import com.softsync.zerock.repository.OrderRepository;

@Service
public class InspectionService {
	   @Autowired
	  ContractRepository contractRepository;
	   
	   @Autowired
	   InspectionRepository inspectionRepository;
	   
		@Autowired
		OrderRepository orderRepository;
		
		/*
		 * public Inspection createInspection(String orderNo, LocalDate inspecPlan,
		 * Integer quantity) { Orders orders =
		 * orderRepository.findById(orderNo).orElseThrow(() -> new
		 * IllegalArgumentException("Invalid order ID")); Inspection inspection = new
		 * Inspection(); inspection.setOrders(orders);
		 * inspection.setInspecPlan(inspecPlan); inspection.setQuantity(quantity);
		 * return inspectionRepository.save(inspection); }
		 * 
		 * public List<Inspection> getInspectionsByOrderNo(String orderNo) {
		 * List<Inspection> inspections =
		 * inspectionRepository.findByOrdersOrderNo(orderNo); return inspections; }
		 * public void updateInspection(Inspection inspection) {
		 * inspectionRepository.save(inspection); }
		 * 
		 * public List<Inspection> getAllInspections() { return
		 * inspectionRepository.findAll(); }
		 */

		   // 새로운 Inspection 엔티티를 생성
		   public Inspection createInspection(String orderNo, LocalDate inspecPlan, Integer quantity) {
		       Orders orders = orderRepository.findById(orderNo).orElseThrow(() -> new IllegalArgumentException("Invalid order ID: " + orderNo));
		       Inspection inspection = new Inspection();
		       inspection.setOrders(orders);
		       inspection.setInspecPlan(inspecPlan);
		       inspection.setQuantity(quantity);
		       return inspectionRepository.save(inspection);
		   }

		   // 특정 orderNo에 대한 Inspection 목록을 반환
		   public List<Inspection> getInspectionsByOrderNo(String orderNo) {
		       return inspectionRepository.findByOrdersOrderNo(orderNo);
		   }

		   // 모든 Inspection 엔티티를 반환
		   public List<Inspection> getAllInspections() {
		       return inspectionRepository.findAll();
		   }

		   // 특정 Inspection 엔티티를 업데이트
		   public void updateInspection(Inspection inspection) {
		       inspectionRepository.save(inspection);
		   }
}
 