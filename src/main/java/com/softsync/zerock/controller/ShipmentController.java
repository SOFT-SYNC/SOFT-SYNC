package com.softsync.zerock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.softsync.zerock.entity.Shipment;
import com.softsync.zerock.entity.ShipmentList;
import com.softsync.zerock.repository.ShipmentListRepository;
import com.softsync.zerock.repository.ShipmentRepository;
import com.softsync.zerock.service.ShipmentListService;
import com.softsync.zerock.service.ShipmentService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;
     
    @Autowired
    private ShipmentListService shipmentListService;
    
    @Autowired
    private ShipmentRepository shipmentRepository;
    
    @Autowired
    private ShipmentListRepository shipmentListRepository;

    @GetMapping("/shipping_list")
    public String showShipmentPage(Model model,
                                   @RequestParam(value = "pageShipment", required = false, defaultValue = "0") int pageShipment,
                                   @RequestParam(value = "pageShipmentList", required = false, defaultValue = "0") int pageShipmentList,
                                   @RequestParam(value = "searchField", required = false) String searchField,
                                   @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
                                   @RequestParam(value = "searchType", required = false) String searchType,
                                   HttpSession session) {
    	
    
        Pageable pageableShipment = PageRequest.of(pageShipment, 10);        
        Page<Shipment> shipments = Page.empty();       
        boolean shipmentSearchEmpty = false;
        
        if ("shipment".equals(searchType) && searchField != null && searchKeyword != null && !searchKeyword.isEmpty()) {
            session.setAttribute("shipmentSearchField", searchField);
            session.setAttribute("shipmentSearchKeyword", searchKeyword);
            try {
                switch (searchField) {
                    case "itemCode":
                        shipments = shipmentRepository.findByProcurementPlan_Item_ItemCodeContaining(searchKeyword, pageableShipment);
                        break;
                    case "itemName":
                        shipments = shipmentRepository.findByProcurementPlan_Item_ItemNameContaining(searchKeyword, pageableShipment);
                        break;
                    case "productName":
                        shipments = shipmentRepository.findByProcurementPlan_ProductionPlan_ProductNameContaining(searchKeyword, pageableShipment);
                        break;
                    case "productionStartDate":
                        shipments = shipmentRepository.findByProcurementPlan_ProductionPlan_ProductionStartDateContaining(searchKeyword, pageableShipment);
                        break;
                    case "itemQuantity":
                        shipments = shipmentRepository.findByProcurementPlan_ProductionPlan_ItemQuantityContaining(Integer.parseInt(searchKeyword), pageableShipment);
                        break;
                    case "inventoryQuantity":
                        shipments = shipmentRepository.findByInventory_QuantityContaining(Integer.parseInt(searchKeyword), pageableShipment);
                        break;
                    default:
                        shipments = shipmentRepository.findAll(pageableShipment);
                        break;
                }
            } catch (Exception e) {
                shipments = shipmentRepository.findAll(pageableShipment);
            }
        } else {
            shipments = shipmentRepository.findAll(pageableShipment);
            session.removeAttribute("shipmentSearchField");
            session.removeAttribute("shipmentSearchKeyword");
        }

        if (shipments.isEmpty()) {
            shipmentSearchEmpty = true;
            shipments = shipmentRepository.findAll(pageableShipment);
        }
        
        Pageable pageableShipmentList = PageRequest.of(pageShipmentList, 10);
        Page<ShipmentList> shipmentLists = Page.empty();
        boolean shipmentListSearchEmpty = false;
        
        if ("shipmentList".equals(searchType) && searchField != null && searchKeyword != null && !searchKeyword.isEmpty()) {
            session.setAttribute("shipmentListSearchField", searchField);
            session.setAttribute("shipmentListSearchKeyword", searchKeyword);
            try {
                switch (searchField) {
                    case "itemCode":
                        shipmentLists = shipmentListRepository.findByShipment_ProcurementPlan_ProductionPlan_Item_ItemCodeContaining(searchKeyword, pageableShipmentList);
                        break;
                    case "itemName":
                        shipmentLists = shipmentListRepository.findByShipment_ProcurementPlan_ProductionPlan_Item_ItemName(searchKeyword, pageableShipmentList);
                        break;
                    case "productName":
                        shipmentLists = shipmentListRepository.findByShipment_ProcurementPlan_ProductionPlan_ProductName(searchKeyword, pageableShipmentList);
                        break;
                    case "productionStartDate":
                        shipmentLists = shipmentListRepository.findByShipment_ProcurementPlan_ProductionPlan_ProductionStartDate(searchKeyword, pageableShipmentList);
                        break;
                    case "inventoryQuantity":
                        shipmentLists = shipmentListRepository.findByInventoryQuantity(searchKeyword, pageableShipmentList);
                        break;
                    case "quantity":
                        shipmentLists = shipmentListRepository.findByQuantity(Integer.parseInt(searchKeyword), pageableShipmentList);
                        break;
                    case "shipmentDate":
                        shipmentLists = shipmentListRepository.findByShipmentDate(searchKeyword, pageableShipmentList);
                    default:
                        shipmentLists = shipmentListRepository.findAll(pageableShipmentList);
                        break;
                }
            } catch (Exception e) {
                shipmentLists = shipmentListRepository.findAll(pageableShipmentList);
            }
        } else {
            shipmentLists = shipmentListRepository.findAll(pageableShipmentList);
            session.removeAttribute("shipmentListSearchField");
            session.removeAttribute("shipmentListSearchKeyword");
        }

        if (shipmentLists.isEmpty()) {
            shipmentListSearchEmpty = true;
            shipmentLists = shipmentListRepository.findAll(pageableShipmentList);
        }

        model.addAttribute("shipments", shipments);
        model.addAttribute("shipmentLists", shipmentLists); 
        model.addAttribute("searchField", searchField);
        model.addAttribute("searchKeyword", searchKeyword);
        model.addAttribute("shipmentSearchEmpty", shipmentSearchEmpty);
        model.addAttribute("shipmentListSearchEmpty", shipmentListSearchEmpty);
        model.addAttribute("pageShipment", pageShipment);
        model.addAttribute("pageShipmentList", pageShipmentList);

        return "/materials/shipping_list";
    }
    }

