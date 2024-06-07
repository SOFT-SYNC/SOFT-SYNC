package com.softsync.zerock.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.softsync.zerock.entity.ReceiveList;

public interface ReceiveListRepository extends JpaRepository<ReceiveList, Long>{

	 List<ReceiveList> findByReceiving_ReceiveNumber(int receiveNumber);
	 
	 
	// 오늘 날짜에 해당하는 입고 수량 조회 쿼리
	    @Query("SELECT COALESCE(SUM(rl.quantity), 0) FROM ReceiveList rl WHERE rl.sysdate = :date")
	    int getReceivingQuantityByDate(LocalDate date);
}
