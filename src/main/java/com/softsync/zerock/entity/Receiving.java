package com.softsync.zerock.entity;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

//@Data
@Entity
@Getter
@Setter
@Table(name = "receiving")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Receiving { //입고


   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int receiveNumber;  //입고번호
   

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_no", nullable = false)
    private Orders orders;
    
    
    @OneToMany(mappedBy = "receiving", fetch = FetchType.LAZY)
    @JsonIgnore //거래명세서용 무한참조 방지
    private List<ReceiveList> receiveLists;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore 
    @JoinColumn(name = "inventory_id", nullable = true)
    private Inventory inventory; // 재고

   
   @Column(nullable = true)
   private Date receiveDate;  //입고마감일
   
   @Column(nullable = false, columnDefinition = "int(5) DEFAULT '0'")
   private int receiveQuantity; //총입고량
   
 
   @Column (nullable = false, length = 1, columnDefinition = "CHAR(1) DEFAULT 'N'")
   private char receiveClosingYn; // 입고상태
   
   @Column (nullable = false, length = 1, columnDefinition = "CHAR(1) DEFAULT 'N'")
   private char inspectYn; // 진척검수 진행여부 확인용 
   

   
}
