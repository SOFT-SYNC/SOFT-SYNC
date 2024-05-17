package com.softsync.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softsync.zerock.entity.Contract;
import com.softsync.zerock.repository.ContractRepository;

@Service
public class ReceivingService {

//	@Autowired
//	RecivingRepository recivingRepository;
	
	@Autowired
	ContractRepository contractRepository;
	
	public List<Contract> resiving(){
		
		return contractRepository.findAll();
	}
}
