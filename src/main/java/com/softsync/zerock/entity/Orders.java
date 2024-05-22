package com.softsync.zerock.entity;

import java.time.LocalDate;
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

   
	/*
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "employee_id", nullable = false) private User employeeId;
	 */
   
   @ManyToOne
   @JoinColumn(name = "brn", nullable = true)
   private Company company;

   @ManyToOne
   @JoinColumn(name = "item_code", nullable = true)
   private Item item;
   
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "contract_number", nullable = true)
   private Contract contract;
   
   
	/*
	 * @OneToOne(mappedBy = "orders", fetch = FetchType.LAZY, cascade =
	 * CascadeType.ALL, orphanRemoval = true) private Receiving Receivings; //입고 1:n
	 */   
   @Column (nullable = false)
   private Integer orderQuantity;   //발주량
   
   @Column (nullable = false)
   private LocalDate orderDate;

   @Column (nullable = false)
   private Date receiveDuedate;
   
   @Column(nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N'")
   private String orderYn = "N";
   
   @Column (nullable = false,columnDefinition = "CHAR(1) DEFAULT 'N'")
   private String receiptYn = "N";
   
   @Column (nullable = true)
   private String orderNote;
}