package com.softsync.zerock.entity;

import java.time.LocalDate;

import org.hibernate.annotations.DynamicUpdate;

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
@Table(name="inspection_list")
@DynamicUpdate
public class InspectionList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본키
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspecNo", nullable = false)
    private Inspection inspection;

    @Column (nullable = false)
    private String percent; //진행률
    
	@Column (nullable = true)
	private LocalDate inspecDate; //검수일
	
	@Column (nullable =true)
	private String inspecNote; //비고
	
	 
}
