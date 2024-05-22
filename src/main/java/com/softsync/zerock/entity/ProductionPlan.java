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
@Table(name = "production_plans")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ProductionPlan {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본키

    @Column(nullable = false)
    private LocalDate productionStartDate; // 생산 시작일

    @Column(nullable = false)
    private LocalDate productionEndDate; // 생산 종료일
    
    @Column(nullable = false)
    private String productCode; // 생산 제품 코드

    @Column(nullable = false)
    private String productName; // 생산 제품명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item; // 조달 품목

    @Column(nullable = false)
    private int productionQuantity; // 생산 수량

    @Column(nullable = false)
    private int itemQuantity; // 품목 소요 수량
    
 // 기본 생성자
    public ProductionPlan() {
    }

    // Long 타입의 생성자 추가
    public ProductionPlan(Long id) {
        this.id = id;
    }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public LocalDate getProductionStartDate() {
      return productionStartDate;
   }

   public void setProductionStartDate(LocalDate productionStartDate) {
      this.productionStartDate = productionStartDate;
   }

   public LocalDate getProductionEndDate() {
      return productionEndDate;
   }

   public void setProductionEndDate(LocalDate productionEndDate) {
      this.productionEndDate = productionEndDate;
   }

   public String getProductCode() {
      return productCode;
   }

   public void setProductCode(String productCode) {
      this.productCode = productCode;
   }

   public String getProductName() {
      return productName;
   }

   public void setProductName(String productName) {
      this.productName = productName;
   }

   public Item getItem() {
      return item;
   }

   public void setItem(Item item) {
      this.item = item;
   }

   public int getProductionQuantity() {
      return productionQuantity;
   }

   public void setProductionQuantity(int productionQuantity) {
      this.productionQuantity = productionQuantity;
   }

   public int getItemQuantity() {
      return itemQuantity;
   }

   public void setItemQuantity(int itemQuantity) {
      this.itemQuantity = itemQuantity;
   }
    
    
}