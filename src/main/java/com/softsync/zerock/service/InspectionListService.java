package com.softsync.zerock.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softsync.zerock.entity.Inspection;
import com.softsync.zerock.entity.InspectionList;
import com.softsync.zerock.repository.InspectionListRepository;
import com.softsync.zerock.repository.InspectionRepository;

@Service
public class InspectionListService {

	   @Autowired
	   InspectionListRepository inspectionListRepository;
	   @Autowired
	   InspectionRepository inspectionRepository;

	   
	public List<InspectionList> getAllInspectionList() {
		return inspectionListRepository.findAll();
	}
	


}
