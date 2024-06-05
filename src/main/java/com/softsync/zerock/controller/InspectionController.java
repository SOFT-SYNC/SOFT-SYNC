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
import com.softsync.zerock.entity.Orders;
import com.softsync.zerock.service.ContractService;
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


	
	//검수일정 등록
	@PostMapping("/api/inspections/create")
	public Map<String, Object> createInspection(@RequestParam String orderNo, 
	                                            @RequestParam LocalDate inspecPlan,
	                                            @RequestParam Integer quantity,
	                                            @RequestParam Integer times) {

	    
	    // 해당 주문에 대한 정보 가져오기
	    Orders order = orderService.getOrderDetails(orderNo); 

	    List<Inspection> inspections = inspectionService.getInspectionsByOrderNo(orderNo);
	    
	    //진척검수 총량 > 발주량 이면 등록이 안되도록 한 조건문
	    int totalQuantity = 0;
	    for (Inspection inspection : inspections) {
	        totalQuantity += inspection.getQuantity();
	    }

	    if(totalQuantity+quantity > order.getOrderQuantity()) {
	    	System.out.println("이프문 작동");
	    	 Map<String, Object> response = new HashMap<>(); 

	 	    return response; //널값 반환
	    }else {   //발주량 >= 진척검수 수량이면 진행!
	    // 검수 생성
	    Inspection newInspection = inspectionService.createInspection(orderNo, inspecPlan, quantity, times);
	    
	    //orders 안에 진척검수 현황 갱신 : 총 검수갯수 +1
	    String str = order.getInspectionTime();
	    String[] parts = str.split("/"); //문자열 나누기
        int num = Integer.parseInt(parts[1]) + 1; //뒤에숫자 1 추가
        String newStr = parts[0] + "/" + num; //문자열 합치기
        order.setInspectionTime(newStr); //저장시키기
	     orderService.saveOrder(order);  //db에 저장
	       
	       List<Inspection> inspections2 = inspectionService.getInspectionsByOrderNo(orderNo);
	    // 응답에 order 정보와 inspectionList 넣어 반환
	    Map<String, Object> response = new HashMap<>();
	    response.put("order", order);
	    response.put("inspectionList", inspections2);


	    return response;
	    }
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

	@PostMapping("/endInspections")
	  public void endInspections(@RequestBody Map<String, String> request) {
		
		String orderNo = request.get("orderNo");
		orderService.getOrder(orderNo);

        
    }

}
