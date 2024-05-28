package com.softsync.zerock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.softsync.zerock.entity.Shipment;
import com.softsync.zerock.entity.ShipmentList;
import com.softsync.zerock.service.ShipmentListService;
import com.softsync.zerock.service.ShipmentService;

@Controller
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;
    
    @Autowired
    private ShipmentListService shipmentListService;

    @GetMapping("/shipping_list")
    public String showShipmentPage(Model model) {
        // 서비스를 통해 shipment 데이터 가져오기
        List<Shipment> shipments = shipmentService.getAllShipments();
        List<ShipmentList> shipmentList = shipmentListService.getAllShipmentList();
        // 모델에 데이터 추가
        model.addAttribute("shipments", shipments);
        model.addAttribute("shipmentLists", shipmentList);

        // 뷰 이름 반환
        return "/materials/shipping_list";
    }
}
