package com.softsync.zerock.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.softsync.zerock.entity.ShipmentList;

@Repository
public interface ShipmentListRepository extends JpaRepository<ShipmentList, Long> {
	
	
	// 오늘 날짜에 해당하는 출고 수량 조회 쿼리
    @Query("SELECT COALESCE(SUM(sl.quantity), 0) FROM ShipmentList sl WHERE sl.shipmentDate = :date")
    int getShipmentQuantityByDate(LocalDate date);
    
	ShipmentList findById(long id);
	
	Page<ShipmentList> findByShipment_ProcurementPlan_ProductionPlan_Item_ItemCodeContaining(String itemCode, Pageable pageable);
	Page<ShipmentList> findByShipment_ProcurementPlan_ProductionPlan_Item_ItemName(String itemCode, Pageable pageable);
	Page<ShipmentList> findByShipment_ProcurementPlan_ProductionPlan_ProductName(String ProductName, Pageable pageable);
	
	@Query("SELECT p FROM ShipmentList p WHERE str(p.shipment.procurementPlan.productionPlan.productionStartDate) LIKE %:keyword%")
	Page<ShipmentList> findByShipment_ProcurementPlan_ProductionPlan_ProductionStartDate(@Param("keyword") String keyword, Pageable pageable);
	
	Page<ShipmentList> findByShipment_ProcurementPlan_ProductionPlan_ItemQuantity(String itemQuantity, Pageable pageable);
	Page<ShipmentList> findByInventoryQuantity(String inventoryQuantity, Pageable pageable);
	Page<ShipmentList> findByQuantity(Integer quantity, Pageable pageable);
	
	@Query("SELECT p FROM ShipmentList p WHERE str(p.shipmentDate) LIKE %:keyword%")
	Page<ShipmentList> findByShipmentDate(@Param("keyword") String keyword, Pageable pageable);
	
}
