package com.softsync.zerock.entity;

import java.time.LocalDate;

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
@Table(name="inspection")
public class Inspection {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inspecNo; // 기본키
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderNo", nullable = false)
    private Orders orders; //발주 엔터티
	
//  @OneToMany(fetch = FetchType.LAZY)
//    private List<InspectionList> InspectionList;
	  
	@Column (nullable = false)
	private LocalDate inspecPlan; //검수예정일
	
	@Column (nullable = false)
	private Integer quantity; //검수수량
	
	@Column(nullable = false)
	private Integer times; //차수
	
	@Column(nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N'")
	private char inspecYn = 'N';
	

    @Column (nullable = true)
    private String percent; //진행률
    
	@Column (nullable = true)
	private LocalDate inspecDate; //검수일
	
	@Column (nullable =true)
	private String inspecNote; //비고
	
}
