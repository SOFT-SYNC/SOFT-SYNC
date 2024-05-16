package com.softsync.zerock.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
@Table (name = "procurement")
@DynamicUpdate
public class Procurement {
   
   @Id
   private String procNo;
   
   @ManyToOne(fetch = FetchType.LAZY)
   private Item itemCode;

   @ManyToOne(fetch = FetchType.LAZY)
   private Production prodPlanNo;
   
   @OneToMany(fetch = FetchType.LAZY)
    private List<Orders> orders;
   
   @Column(nullable =true)
   private Integer procQuantity;
   
   @Column(nullable = true)
   private Integer procTerm;
   
   @Column(nullable = true)
   private Date procDuedate;
   
   @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
   private Integer consumed;
}