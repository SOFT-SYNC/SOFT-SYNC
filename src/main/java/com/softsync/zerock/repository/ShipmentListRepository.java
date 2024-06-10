package com.softsync.zerock.repository;

import com.softsync.zerock.entity.ShipmentList;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentListRepository extends JpaRepository<ShipmentList, Long> {
	
	
	// 오늘 날짜에 해당하는 출고 수량 조회 쿼리
    @Query("SELECT COALESCE(SUM(sl.quantity), 0) FROM ShipmentList sl WHERE sl.shipmentDate = :date")
    int getShipmentQuantityByDate(LocalDate date);
    
	ShipmentList findById(long id);
}
