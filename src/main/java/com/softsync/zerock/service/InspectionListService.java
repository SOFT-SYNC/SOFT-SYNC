package com.softsync.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softsync.zerock.entity.InspectionList;
import com.softsync.zerock.repository.InspectionListRepository;

@Service
public class InspectionListService {

	   @Autowired
	   InspectionListRepository inspectionListRepository;
	   
	public List<InspectionList> getAllInspectionList() {
		return inspectionListRepository.findAll();
	}

}
