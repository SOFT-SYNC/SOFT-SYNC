package com.softsync.zerock.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softsync.zerock.DTO.InventoryPeriodSummaryDTO;
import com.softsync.zerock.entity.InventoryPeriod;
import com.softsync.zerock.repository.InventoryPeriodRepository;

@Service
public class InventoryPeriodService {

    @Autowired
    private InventoryPeriodRepository inventoryPeriodRepository;

    public void save(InventoryPeriod inventoryPeriod) {
        inventoryPeriodRepository.save(inventoryPeriod);
    }

    public List<InventoryPeriodSummaryDTO> getInventoryPeriodSummaries(LocalDate startDate, LocalDate endDate) {
        return inventoryPeriodRepository.findInventoryPeriodSummaries(startDate, endDate);
    }
}
