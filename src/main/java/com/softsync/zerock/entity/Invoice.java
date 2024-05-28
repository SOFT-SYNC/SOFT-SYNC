package com.softsync.zerock.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="invoice") //거래명세서
public class Invoice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int num;
	
	@Column(nullable = true)
	private LocalDate publishDate;  //발행일
	
	@Column(nullable = true, length = 1, columnDefinition = "CHAR(1) DEFAULT 'N'")
	private char publishYn; // 발행 여부
	
	@OneToOne
    @JoinColumn(name = "receiving_id", referencedColumnName = "receiveNumber")
    private Receiving receiving;
}
