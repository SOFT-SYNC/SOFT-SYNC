package com.softsync.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softsync.zerock.entity.Orders;
import com.softsync.zerock.repository.OrderRepository;

@Service
public class ReceivingService {

//	@Autowired
//	RecivingRepository recivingRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	public List<Orders> resiving(){
		
		return orderRepository.findAll();
	}
}
