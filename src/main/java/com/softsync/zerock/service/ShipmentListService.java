package com.softsync.zerock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.softsync.zerock.entity.ShipmentList;
import com.softsync.zerock.repository.ShipmentListRepository;


@Service
public class ShipmentListService {
    
    @Autowired
    private ShipmentListRepository shipmentListRepository;

    // save 메소드 구현
    public ShipmentList save(ShipmentList shipmentList) {
        return shipmentListRepository.save(shipmentList);
    }
    
    
}
