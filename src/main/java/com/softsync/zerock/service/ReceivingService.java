package com.softsync.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softsync.zerock.entity.Receiving;
import com.softsync.zerock.repository.ReceivingRepository;

@Service
public class ReceivingService {

//	@Autowired
//	RecivingRepository recivingRepository;
	
	@Autowired
	ReceivingRepository receivingRepository;
	
	public List<Receiving> resiving(){
		
		return receivingRepository.findAll();
	}
}
