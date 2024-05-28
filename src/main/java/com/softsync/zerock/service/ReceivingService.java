package com.softsync.zerock.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import com.softsync.zerock.entity.Inventory;
import com.softsync.zerock.entity.Item;
import com.softsync.zerock.entity.Orders;
import com.softsync.zerock.entity.ReceiveList;
import com.softsync.zerock.entity.Receiving;
import com.softsync.zerock.repository.InventoryRepository;
import com.softsync.zerock.repository.ReceiveListRepository;
import com.softsync.zerock.repository.ReceivingRepository;

@Service
public class ReceivingService {

	
	@Autowired
	ReceivingRepository receivingRepository;
	
	@Autowired
	ReceiveListRepository receiveListRepository;
	
	@Autowired
	InventoryRepository inventoryRepository;
	
	
	//하단(입고 내역) 페이징
	 public Page<ReceiveList> getAllItems(@PageableDefault(size = 10) Pageable pageable) {
	        return receiveListRepository.findAll(pageable);
	  }

	 
	//입고 리스트
	public List<Receiving> resiving(){
		
		return receivingRepository.findAll();
	}
	
	//입고내역 리스트
	public List<ReceiveList> resList(){
		
		return receiveListRepository.findAll();
	}
	
	public void saveReceiving(Orders order) {
		 Receiving receiving = new Receiving();
	        receiving.setOrders(order);
	        receiving.setReceiveClosingYn('N');

	        // 입고 정보 저장
	        receivingRepository.save(receiving);
	}
	
	//입고처리  > 입고테이블 : 입고총량 / 입고내역 :컬럼추가 / 인벤토리 : 재고추가됨
	public void updateReceiving(int quantity, String num) {
		System.out.println("입고 서비스 : 업데이트 입고량" + num);
		
		Receiving receiving = receivingRepository.getReferenceById(num);
		ReceiveList receiveList = new ReceiveList();
		receiveList.setQuantity(quantity);
		
		receiving.setReceiveQuantity(receiving.getReceiveQuantity() + quantity);//총계
		
		receiveList.setReceiving(receiving);
		
		 // 현재 날짜를 나타내는 java.util.Date 객체 생성
	    java.util.Date utilDate = new java.util.Date();
	    // java.util.Date를 java.sql.Date로 변환
	    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	    receiveList.setSysdate(sqlDate);
		
		receivingRepository.save(receiving); //입고
		receiveListRepository.save(receiveList); //입고 내역
		
		Item item= receiving.getOrders().getItem();
		Long itemId = item.getId();
		Optional<Inventory> optionalInventory = inventoryRepository.findById(itemId);
		
		 Inventory inventory = optionalInventory.get();
		 inventory.setQuantity(inventory.getQuantity() + quantity); //재고량 업데잍스
		inventoryRepository.save(inventory);
		
	}
	
	//마감처리    receive_closing_yn : (디폴트)N -> Y 
	public void endRecieving(String num) {
		System.out.println("입고 서비스 : 입고마갑" + num);
		
	    java.util.Date utilDate = new java.util.Date();
	    // java.util.Date를 java.sql.Date로 변환
	    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	    
		Receiving receiving = receivingRepository.getReferenceById(num);
		receiving.setReceiveDate(sqlDate);
		receiving.setReceiveClosingYn('Y');
		
		
		receivingRepository.save(receiving);
	}
	
	
}
