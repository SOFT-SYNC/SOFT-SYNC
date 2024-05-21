package com.softsync.zerock.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
@Table(name = "receiving")
public class Reciving {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long receiveNumber;  //입고번호
	
//	@ManyToOne(fetch = FetchType.LAZY)  //품목 빼도 될거같은디..
//    private Item item;
	
	   @ManyToOne(fetch = FetchType.LAZY)  //발주서
	    @JoinColumn(name="orderNo", nullable = false)
	    private Orders orders;
	
	@Column(nullable = true)
	private Date receiveDate;  //입고일
	
	@Column(nullable = true, columnDefinition = "int(5) DEFAULT '1'")
	private int receiveQuantity; //입고수량
	
	@Column (nullable = false, length = 1, columnDefinition = "CHAR(1) DEFAULT 'N'")
	private char receiveClosingYn; // 입고상태
	

	
}
