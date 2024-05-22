package com.softsync.zerock.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "procurement_plans")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ProcurementPlan {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본키

   @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productionplan_id", nullable = false)
    private ProductionPlan productionPlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item; // 조달 품목

    @Column(nullable = false)
    private int procurementInterval; // 조달 간격(일)
    
    @Column(nullable = false)
    private int procurementQuantity; // 조달 수량

    @Column(nullable = false)
    private LocalDate procurementDueDate; // 조달 납기일

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public ProductionPlan getProductionPlan() {
      return productionPlan;
   }

   public void setProductionPlan(ProductionPlan productionPlan) {
      this.productionPlan = productionPlan;
   }

   public Item getItem() {
      return item;
   }

   public void setItem(Item item) {
      this.item = item;
   }

   public int getProcurementInterval() {
      return procurementInterval;
   }

   public void setProcurementInterval(int procurementInterval) {
      this.procurementInterval = procurementInterval;
   }

   public int getProcurementQuantity() {
      return procurementQuantity;
   }

   public void setProcurementQuantity(int procurementQuantity) {
      this.procurementQuantity = procurementQuantity;
   }

   public LocalDate getProcurementDueDate() {
      return procurementDueDate;
   }

   public void setProcurementDueDate(LocalDate procurementDueDate) {
      this.procurementDueDate = procurementDueDate;
   }

    // Getter and Setter
    
}