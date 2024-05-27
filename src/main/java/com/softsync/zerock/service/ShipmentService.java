package com.softsync.zerock.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softsync.zerock.entity.Shipment;
import com.softsync.zerock.repository.ShipmentRepository;

@Service
public class ShipmentService {
	@Autowired
	private ShipmentRepository shipmentRepository;

	public List<Shipment> getAllShipments() {
		return shipmentRepository.findAll();
	}
	
	public Shipment findById(Long id) {
        return shipmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Shipment not found with id: " + id));
    }
}
