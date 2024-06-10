package com.softsync.zerock.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softsync.zerock.entity.ShipmentList;
import com.softsync.zerock.repository.ShipmentListRepository;

import jakarta.transaction.Transactional;


@Service
public class ShipmentListService {
	 private static final Logger logger = LoggerFactory.getLogger(ShipmentService.class);
	 
    @Autowired
    private ShipmentListRepository shipmentListRepository;

    // save 메소드 구현
    public ShipmentList save(ShipmentList shipmentList) {
        return shipmentListRepository.save(shipmentList);
    }
    
    public List<ShipmentList> getAllShipmentList() {
    	return shipmentListRepository.findAll();
    }
    
    public ShipmentList findById(long id) {
        return shipmentListRepository.findById(id);
    }
    
    @Transactional
    public void deleteShipmentListById(Long shipmentListId) {
        ShipmentList shipmentList = shipmentListRepository.findById(shipmentListId).orElse(null);
        if (shipmentList != null) {
            logger.info("Deleting shipmentList: {}", shipmentList);
            shipmentListRepository.delete(shipmentList);
        } else {
            logger.warn("ShipmentList not found with ID: {}", shipmentListId);
            throw new IllegalArgumentException("ShipmentList not found with ID: " + shipmentListId);
        }
    }
}

