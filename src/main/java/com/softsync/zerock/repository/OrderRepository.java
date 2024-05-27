package com.softsync.zerock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softsync.zerock.entity.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders,String>{

	  Orders findByOrderNo(String orderNo);

}
