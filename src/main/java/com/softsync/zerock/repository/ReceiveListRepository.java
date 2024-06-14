package com.softsync.zerock.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.softsync.zerock.entity.Contract;
import com.softsync.zerock.entity.ReceiveList;
import com.softsync.zerock.entity.Receiving;

public interface ReceiveListRepository extends JpaRepository<ReceiveList, Long>{

	 List<ReceiveList> findByReceiving_ReceiveNumber(int receiveNumber);
	 
	 
	// 오늘 날짜에 해당하는 입고 수량 조회 쿼리
	    @Query("SELECT COALESCE(SUM(rl.quantity), 0) FROM ReceiveList rl WHERE rl.sysdate = :date")
	    int getReceivingQuantityByDate(LocalDate date);
	    
	    Page<ReceiveList> findByReceiving_Orders_Contract_Item_ItemCodeContaining(String itemCode, Pageable pageable);
	    Page<ReceiveList> findByReceiving_Orders_Contract_Item_ItemNameContaining(String itemName, Pageable pageable);
	    
	    Page<ReceiveList> findByReceiving_Orders_OrderNoContaining(String searchKeyword, Pageable pageable);

}
