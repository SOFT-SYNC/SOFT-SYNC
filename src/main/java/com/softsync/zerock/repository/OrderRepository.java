package com.softsync.zerock.repository;


import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.softsync.zerock.entity.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders,String>{

	  Orders findByOrderNo(String orderNo);

	  Long countByOrderYn(String orderYn);
	  
	  @Query("SELECT COUNT(*) FROM Orders WHERE orderDate BETWEEN :startDate AND :endDate")
		Long countByOrderDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
