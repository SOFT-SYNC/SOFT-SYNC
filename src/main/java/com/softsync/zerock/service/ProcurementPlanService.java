package com.softsync.zerock.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softsync.zerock.DTO.ProcurementPlanDTO;
import com.softsync.zerock.entity.Inventory;
import com.softsync.zerock.entity.Item;
import com.softsync.zerock.entity.ProcurementPlan;
import com.softsync.zerock.entity.ProductionPlan;
import com.softsync.zerock.entity.Shipment;
import com.softsync.zerock.repository.ItemRepository;
import com.softsync.zerock.repository.ProcurementPlanRepository;
import com.softsync.zerock.repository.ProductionPlanRepository;
import com.softsync.zerock.repository.ShipmentRepository;

@Service
public class ProcurementPlanService {

    @Autowired
    private ProcurementPlanRepository procurementPlanRepository;

    @Autowired
    private ProductionPlanRepository productionPlanRepository;
    
    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private ItemRepository itemRepository;

    public ProcurementPlan createProcurementPlan(ProcurementPlanDTO procurementPlanDTO) {
        ProcurementPlan procurementPlan = new ProcurementPlan();

     // ProductionPlan 및 Item 객체를 데이터베이스에서 조회하여 설정
        Optional<ProductionPlan> productionPlanOpt = productionPlanRepository.findById(procurementPlanDTO.getProductionPlanId());
        Optional<Item> itemOpt = itemRepository.findById(procurementPlanDTO.getItemId());

        if (productionPlanOpt.isPresent() && itemOpt.isPresent()) {
            procurementPlan.setProductionPlan(productionPlanOpt.get());
            procurementPlan.setItem(itemOpt.get());
            procurementPlan.setProcurementInterval(procurementPlanDTO.getProcurementInterval());
            procurementPlan.setProcurementQuantity(procurementPlanDTO.getProcurementQuantity());
            procurementPlan.setProcurementDueDate(procurementPlanDTO.getProcurementDueDate());

            ProcurementPlan savedProcurementPlan = procurementPlanRepository.save(procurementPlan);

            // Item을 통해 Inventory를 조회하여 설정
            Item item = itemOpt.get();
            Inventory inventory = item.getInventory(); // 예시: item에 연결된 inventory 가져오는 방식에 따라 수정
            if (inventory == null) {
                throw new RuntimeException("Inventory not found for the specified item.");
            }
            
            // Shipment 생성 및 저장
            Shipment shipment = new Shipment();
            shipment.setProcurementPlan(savedProcurementPlan);
            shipment.setInventory(inventory);
            shipment.setShipmentDate(LocalDate.now());
            shipmentRepository.save(shipment);

            return savedProcurementPlan;
        } else {
            throw new RuntimeException("Invalid ProductionPlan or Item ID");
        }
    }
}