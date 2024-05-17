package com.softsync.zerock.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.softsync.zerock.entity.ProductionPlan;

public interface ProductionPlanRepository extends JpaRepository<ProductionPlan, Long> {
    Page<ProductionPlan> findByItem_ItemNameContaining(String itemName, Pageable pageable);
    Page<ProductionPlan> findByItem_ItemCodeContaining(String itemCode, Pageable pageable);
    Page<ProductionPlan> findByProductCodeContaining(String productCode, Pageable pageable);
    Page<ProductionPlan> findByProductNameContaining(String productName, Pageable pageable);
    Page<ProductionPlan> findByProductionQuantity(Integer productionQuantity, Pageable pageable);
    Page<ProductionPlan> findByItemQuantity(Integer itemQuantity, Pageable pageable);

    @Query("SELECT p FROM ProductionPlan p WHERE str(p.productionStartDate) LIKE %:keyword%")
    Page<ProductionPlan> findByProductionStartDateContaining(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT p FROM ProductionPlan p WHERE str(p.productionEndDate) LIKE %:keyword%")
    Page<ProductionPlan> findByProductionEndDateContaining(@Param("keyword") String keyword, Pageable pageable);
}