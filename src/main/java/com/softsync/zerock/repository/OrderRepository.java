package com.softsync.zerock.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

	/*
	 * @Query("select p,i,c from Orders p inner join item i on p.item.itemCode = i.itemCode"
	 * +
	 * "inner join Contract c on i.contract.contractNumber = c.contractNumber where p.purchClosingYn !='N' ORDER BY p.orderDate"
	 * ) <Order> findAllOrderList(@Param("orderNo")String purchNumber);
	 */
}
