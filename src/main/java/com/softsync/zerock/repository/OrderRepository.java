package com.softsync.zerock.repository;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.softsync.zerock.entity.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders,String>{

	  Orders findByOrderNo(String orderNo);

	  Long countByReceiptYn(String orderYn);
	  
	  @Query("SELECT COUNT(*) FROM Orders WHERE orderDate BETWEEN :startDate AND :endDate")
		Long countByOrderDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

	// 오늘 날짜에 해당하는 출고 수량 조회 쿼리
//	    @Query("SELECT COALESCE(SUM(ol.orderQuantity), 0) FROM orders ol WHERE ol.orderDate = :date")
//	    int getOrdersQuantityByDate(LocalDate date);

	  List<Orders> findByOrderDate(LocalDate orderDate);
	  List<Orders> findByReceiveDuedate(Date receiveDuedate);
}
