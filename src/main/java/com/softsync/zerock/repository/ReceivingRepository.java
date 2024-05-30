package com.softsync.zerock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softsync.zerock.entity.Orders;
import com.softsync.zerock.entity.Receiving;


@Repository
public interface ReceivingRepository extends JpaRepository<Receiving, Integer> {

	Receiving findByOrders(Orders order);
	
}
