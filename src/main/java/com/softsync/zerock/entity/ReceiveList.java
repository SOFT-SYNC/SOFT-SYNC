package com.softsync.zerock.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "receiveList")
public class ReceiveList {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int num;  //번호
	
	@ManyToOne
	@JoinColumn(name = "receiving_receiveNumber") // 외래 키 설정
    private Receiving receiving;
	
	@Column(nullable = false)
	private int quantity;  //입고량
	
	@Column(nullable = false)
	private Date sysdate;  //입고일

	
	
}
