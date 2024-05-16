package com.softsync.zerock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softsync.zerock.entity.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders,String>{

	
	/*
	 * @Query("select p,i,c from Orders p inner join item i on p.item.itemCode = i.itemCode"
	 * +
	 * "inner join Contract c on i.contract.contractNumber = c.contractNumber where p.purchClosingYn !='N' ORDER BY p.orderDate"
	 * ) <Order> findAllOrderList(@Param("orderNo")String purchNumber);
	 */
	 
	

}
