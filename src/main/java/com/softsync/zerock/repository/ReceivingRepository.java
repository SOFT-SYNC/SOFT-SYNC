package com.softsync.zerock.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softsync.zerock.entity.Orders;
import com.softsync.zerock.entity.Receiving;


@Repository
public interface ReceivingRepository extends JpaRepository<Receiving, Integer> {


	Receiving findByOrders(Orders order);
	Page<Receiving> findByOrders_Contract_Item_ItemCodeContaining(String searchKeyword, Pageable  pageable);
	Page<Receiving> findByOrders_Contract_Item_ItemNameContaining(String searchKeyword, Pageable  pageable);
	



}
