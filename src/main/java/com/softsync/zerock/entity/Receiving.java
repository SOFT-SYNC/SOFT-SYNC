package com.softsync.zerock.entity;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
@Table(name = "receiving")
public class Receiving {


   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private int receiveNumber;  //입고번호
   

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_no", nullable = false)
    private Orders orders;
    
    
    @OneToMany(mappedBy = "receiving", fetch = FetchType.LAZY)
    private List<ReceiveList> receiveLists;

   
   @Column(nullable = true)
   private Date receiveDate;  //입고일
   
   @Column(nullable = false, columnDefinition = "int(5) DEFAULT '0'")
   private int receiveQuantity; //총입고량
   
 
   @Column (nullable = false, length = 1, columnDefinition = "CHAR(1) DEFAULT 'N'")
   private char receiveClosingYn; // 입고상태
   

   
}
