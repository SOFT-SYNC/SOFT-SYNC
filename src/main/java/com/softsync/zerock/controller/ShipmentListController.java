package com.softsync.zerock.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softsync.zerock.DTO.ShipmentListDTO;
import com.softsync.zerock.entity.Inventory;
import com.softsync.zerock.entity.InventoryPeriod;
import com.softsync.zerock.entity.Shipment;
import com.softsync.zerock.entity.ShipmentList;
import com.softsync.zerock.repository.InventoryRepository;
import com.softsync.zerock.service.InventoryPeriodService;
import com.softsync.zerock.service.ShipmentListService;
import com.softsync.zerock.service.ShipmentService;

@RestController
@RequestMapping("/api")
public class ShipmentListController {

    @Autowired
    private ShipmentListService shipmentListService;
    
    @Autowired
    private InventoryRepository inventoryRepository;
    
    @Autowired
    private InventoryPeriodService inventoryPeriodService;

    @Autowired
    private ShipmentService shipmentService; // ShipmentService 추가

    @PostMapping("/saveShipmentList")
    public ResponseEntity<String> saveShipmentList(@ModelAttribute ShipmentListDTO shipmentListDTO) {
        try {
            ShipmentList shipmentList = new ShipmentList();

            // ShipmentService를 사용하여 Shipment 객체를 가져옴
            Shipment shipment = shipmentService.findById(shipmentListDTO.getShipmentId());
            shipmentList.setShipment(shipment);

            // shipmentId 값을 콘솔에 출력
            System.out.println("Shipment ID: " + shipmentListDTO.getShipmentId());

            shipmentList.setQuantity(shipmentListDTO.getQuantity());
            System.out.println("Shipment Quantity: " + shipmentListDTO.getQuantity());

            // Shipment의 Inventory의 재고수량 업데이트
            updateInventoryQuantity(shipment, shipmentListDTO.getQuantity());

            shipmentList.setInventoryQuantity(shipment.getInventory().getQuantity()); // 출고 시점의 재고 수량 설정
            shipmentList.setShipmentDate(LocalDate.now());

            // ShipmentList 저장
            shipmentListService.save(shipmentList);

            // InventoryPeriod 저장
            InventoryPeriod inventoryPeriod = new InventoryPeriod();
            inventoryPeriod.setInventory(shipment.getInventory());
            inventoryPeriod.setShipmentList(shipmentList);
            inventoryPeriod.setDate(LocalDate.now());
            inventoryPeriodService.save(inventoryPeriod);

            return new ResponseEntity<>("Shipment List and Inventory Period saved successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to save Shipment List and Inventory Period: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Shipment의 Inventory의 재고수량을 업데이트하는 메서드
    private void updateInventoryQuantity(Shipment shipment, Integer quantity) {
        Inventory inventory = shipment.getInventory();
        if (inventory != null) {
            int currentQuantity = inventory.getQuantity();
            if (currentQuantity >= quantity) {
                inventory.setQuantity(currentQuantity - quantity);
                // 업데이트된 Inventory를 저장
                inventoryRepository.save(inventory);
            } else {
                throw new RuntimeException("Not enough inventory for the shipment");
            }
        } else {
            throw new RuntimeException("Inventory not found for the shipment");
        }
    }
    
}