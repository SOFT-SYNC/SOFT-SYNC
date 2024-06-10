package com.softsync.zerock.service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softsync.zerock.entity.Shipment;
import com.softsync.zerock.repository.ShipmentListRepository;
import com.softsync.zerock.repository.ShipmentRepository;

@Service
public class ShipmentService {
	@Autowired
	private ShipmentRepository shipmentRepository;

	public List<Shipment> getAllShipments() {
		return shipmentRepository.findAll();
	}
	@Autowired
	ShipmentListRepository shipmentListRepository;
	
	public Shipment findById(Long id) {
        return shipmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Shipment not found with id: " + id));
    }
    // 오늘 날짜에 해당하는 출고 수량 조회
    public int getShipmentQuantityByDate(LocalDate date) {
        int shipmentQuantity = shipmentListRepository.getShipmentQuantityByDate(date);
        System.out.println("Today's shipment quantity: " + shipmentQuantity);
        return shipmentQuantity;
    }
    

}
