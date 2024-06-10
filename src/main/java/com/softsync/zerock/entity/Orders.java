package com.softsync.zerock.entity;

import java.time.LocalDate;
import java.util.Date;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="orders")
@DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Orders {

   @Id
   private String orderNo;

   
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "brn", nullable = true)
   private Company company;

   @ManyToOne
   @JoinColumn(name = "item_code", nullable = true)
   private Item item;
   
   @ManyToOne
   @JoinColumn(name = "contract_number", nullable = true)
   private Contract contract;
   
   @Column (nullable = false)
   private String inspectionTime ;   //진척검수 진행횟수
   
   @Column (nullable = false)
   private char inspectYNG = 'N' ;   //진척검수 마감여부 Y N G
 
   @OneToOne(mappedBy = "orders", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
   @JsonIgnore //거래명세서용 무한참조 방지
   private Receiving receiving;  //입고 1:1

   @Column (nullable = false)
   private Integer orderQuantity;   //발주량
   
   @Column (nullable = false)
   private LocalDate orderDate;

   @Column (nullable = false)
   private Date receiveDuedate; //납기일
   
   @Column(nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N'")
   private String orderYn = "N";
   
   @Column (nullable = false,columnDefinition = "CHAR(1) DEFAULT 'N'")
   private String receiptYn = "N";
   
   @Column (nullable = true)
   private String orderNote;
   
   @Column (nullable = true)
   private String totalPrice;
}