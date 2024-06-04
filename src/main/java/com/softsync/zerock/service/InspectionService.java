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

		/*
		 * // @Autowired // InspectionListRepository inspectionListRepository;
		 */		@Autowired
		OrderRepository orderRepository;


		   public Inspection createInspection(String orderNo, LocalDate inspecPlan, Integer quantity, Integer times) {
		       Orders orders = orderRepository.findById(orderNo).orElseThrow(() -> new IllegalArgumentException("Invalid order ID: " + orderNo));
		       Inspection inspection = new Inspection();
		       inspection.setOrders(orders);
		       inspection.setInspecPlan(inspecPlan);
		       inspection.setQuantity(quantity);
		       inspection.setTimes(times);
		       inspection.setPercent("-");
		       return inspectionRepository.save(inspection);
		   }
		   
		   public void save(Inspection inspection) {
			   inspectionRepository.save(inspection); //6/4 추가김
		   }
		   
			/*
			 * // 검수 완료 public InspectionList completeInspection(Long inspecNo, LocalDate
			 * inspecDate, Integer percent) { Inspection inspection =
			 * inspectionRepository.findById(inspecNo) .orElseThrow(() -> new
			 * IllegalArgumentException("Invalid inspection ID: " + inspecNo));
			 * InspectionList inspectionList = new InspectionList();
			 * inspectionList.setInspection(inspection);
			 * inspectionList.setInspecDate(inspecDate); inspectionList.setPercent(percent);
			 * return inspectionListRepository.save(inspectionList); }
			 */

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


//		public void saveInspectionList(InspectionList inspectionList) {
//			inspectionListRepository.save(inspectionList);
//			
//		}

		public Inspection getinspectionByInspecNo(Long inspecNo) {
			Inspection inspection = inspectionRepository.findByInspecNo(inspecNo);
			return inspection;
		}


			
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
	
}
 