package com.softsync.zerock.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softsync.zerock.DTO.ProcurementPlanDTO;
import com.softsync.zerock.entity.Item;
import com.softsync.zerock.entity.ProcurementPlan;
import com.softsync.zerock.entity.ProductionPlan;
import com.softsync.zerock.repository.ItemRepository;
import com.softsync.zerock.repository.ProcurementPlanRepository;
import com.softsync.zerock.repository.ProductionPlanRepository;

@Service
public class ProcurementPlanService {

    @Autowired
    private ProcurementPlanRepository procurementPlanRepository;

    @Autowired
    private ProductionPlanRepository productionPlanRepository;

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

            return procurementPlanRepository.save(procurementPlan);
        } else {
            throw new RuntimeException("Invalid ProductionPlan or Item ID");
        }
    }
}