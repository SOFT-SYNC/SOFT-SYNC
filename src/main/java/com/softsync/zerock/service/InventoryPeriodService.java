package com.softsync.zerock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softsync.zerock.entity.InventoryPeriod;
import com.softsync.zerock.repository.InventoryPeriodRepository;

@Service
public class InventoryPeriodService {

    @Autowired
    private InventoryPeriodRepository inventoryPeriodRepository;

    public void save(InventoryPeriod inventoryPeriod) {
        inventoryPeriodRepository.save(inventoryPeriod);
    }
}