package com.softsync.zerock.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.softsync.zerock.entity.Shipment;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    Page<Shipment> findByProcurementPlan_Item_ItemCodeContaining(String itemCode, Pageable pageable);
    Page<Shipment> findByProcurementPlan_Item_ItemNameContaining(String itemName, Pageable pageable);
    Page<Shipment> findByProcurementPlan_ProductionPlan_ProductNameContaining(String productName, Pageable pageable);
    @Query("SELECT p FROM Shipment p WHERE str(p.procurementPlan.productionPlan.productionStartDate) LIKE %:keyword%")
    Page<Shipment> findByProcurementPlan_ProductionPlan_ProductionStartDateContaining(@Param("keyword") String keyword, Pageable pageable);
    Page<Shipment> findByProcurementPlan_ProductionPlan_ItemQuantityContaining(Integer itemQuantity, Pageable pageable);
    Page<Shipment> findByInventory_QuantityContaining(Integer quantity, Pageable pageable);
}