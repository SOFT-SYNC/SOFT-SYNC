package com.softsync.zerock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softsync.zerock.entity.Contract;
import com.softsync.zerock.entity.Orders;
import com.softsync.zerock.entity.Receiving;
import com.softsync.zerock.service.InvoiceService;

@Controller
public class InvoiceController {
	
	@Autowired
	InvoiceService invoiceService;
	
	@GetMapping("/invoice")
	public String invoiceview(Model model) {
		System.out.println("거래명세서 컨트롤러 : 명세서 조회");
		
		List<Receiving> receivings = invoiceService.getReceivings();
		
		
		model.addAttribute("receivings", receivings);
		
		return"/materials/invoice";
	}
	
	
	@PostMapping("/getReceive")
	public ResponseEntity<?> getReceive(@RequestBody String orderNo ) {
		System.out.println("거래명세서 컨트롤러 : 명세서 상세조회");
		
	
	
		 try {
		        // ObjectMapper 초기화
		        ObjectMapper objectMapper = new ObjectMapper();
		        // JSON 문자열 파싱
		        JsonNode jsonNode = objectMapper.readTree(orderNo);
		        // "orderNo" 키의 값 추출
		        String orderNumber = jsonNode.get("orderNo").asText();
		    	
		        // 주문 번호를 사용하여 수신 정보를 조회
		        Receiving receiving = invoiceService.getReceive(orderNumber); //
		        System.out.println(receiving.getOrders().getItem());
		        System.out.println(receiving.getOrders().getContract());
		        return ResponseEntity.ok(receiving );
		        
		    } catch (Exception e) {
		        // 예외 발생 시 클라이언트에 오류 응답을 반환
		    	   return ResponseEntity.status(500).body("Error occurred while processing request.");
		    }
		}

}
