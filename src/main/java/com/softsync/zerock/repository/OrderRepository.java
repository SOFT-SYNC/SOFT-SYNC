package com.softsync.zerock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softsync.zerock.entity.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders,String>{
	/*
	 * 
	 * 
	 * @Query("select o,i,c from Orders o inner join item i on o.item.itemCode = i.itemCode inner join Contract c on i.contract.contractNo = c.contractNo where o.orderYn !='N' ORDER BY o.orderYn"
	 * ) List <Orders> findAllOrders(@Param("orderNo")String orderNo);
	 * 
	 */
	

}
