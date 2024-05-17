package com.softsync.zerock.entity;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

   
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "employee_id", nullable = false)
   private User employeeId;
   
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "contract_number", nullable = false)
   private Contract contract_number;
   
//   @ManyToOne(fetch = FetchType.LAZY) //조달계획 :입고예정일
//   @JoinColumn(name = "procNo", nullable = false)
//   private ProcurementPlan procNo;
   
   @Column (nullable = false)
   private Integer orderQuantity;
   
   @Column (nullable = false)
   private LocalDateTime orderDate;

   @Column (nullable = false)
   private Date receiveDuedate;
   
   @Column (nullable = false, length = 1, columnDefinition = "CHAR(1) DEFAULT 'N'")
   private String orderYn;
   
   @Column (nullable = false, length = 1, columnDefinition = "CHAR(1) DEFAULT 'N'")
   private String receiptYn;
   
   @Column (nullable = true)
   private String orderNote;
}