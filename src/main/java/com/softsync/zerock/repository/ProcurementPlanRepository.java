package com.softsync.zerock.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.softsync.zerock.entity.ProcurementPlan;

public interface ProcurementPlanRepository extends JpaRepository<ProcurementPlan, Long> {
    Page<ProcurementPlan> findByItem_ItemCodeContaining(String itemCode, Pageable pageable);
    Page<ProcurementPlan> findByItem_ItemNameContaining(String itemName, Pageable pageable);
    Page<ProcurementPlan> findByProductionPlan_ProductCodeContaining(String productCode, Pageable pageable);
    Page<ProcurementPlan> findByProductionPlan_ProductNameContaining(String productName, Pageable pageable);
    Page<ProcurementPlan> findByProductionPlan_ProductionQuantity(Integer productionQuantity, Pageable pageable);
    Page<ProcurementPlan> findByProductionPlan_ItemQuantity(Integer itemQuantity, Pageable pageable);
    Page<ProcurementPlan> findByProcurementQuantity(Integer procurementQuantity, Pageable pageable);
    Page<ProcurementPlan> findByProcurementInterval(Integer procurementInterval, Pageable pageable);
    
    @Query("SELECT p FROM ProcurementPlan p WHERE str(p.procurementDueDate) LIKE %:keyword%")
    Page<ProcurementPlan> findByProcurementDueDateContaining(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT p FROM ProcurementPlan p WHERE str(p.productionPlan.productionStartDate) LIKE %:keyword%")
    Page<ProcurementPlan> findByProductionPlan_ProductionStartDateContaining(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT p FROM ProcurementPlan p WHERE str(p.productionPlan.productionEndDate) LIKE %:keyword%")
    Page<ProcurementPlan> findByProductionPlan_ProductionEndDateContaining(@Param("keyword") String keyword, Pageable pageable);
}