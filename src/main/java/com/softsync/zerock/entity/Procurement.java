package com.softsync.zerock.entity;

import java.util.Date;

import org.hibernate.annotations.DynamicUpdate;

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
@Table (name = "procurement")
@DynamicUpdate
public class Procurement {
	
	@Id
	private String procNo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "itemCode", nullable=true)
	private Item itemCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prodPlanNo", nullable = true)
	private Production prodPlanNo;
	
	@Column(nullable =true)
	private Integer procQuantity;
	
	@Column(nullable = true)
	private Integer procTerm;
	
	@Column(nullable = true)
	private Date procDuedate;
	
	@Column(nullable = false, columnDefinition = "INT DEFAULT 0")
	private Integer consumed;
}
