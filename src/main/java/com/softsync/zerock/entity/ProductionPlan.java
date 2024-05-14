package com.softsync.zerock.entity;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "production_plan")
@DynamicUpdate
public class ProductionPlan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer prodPlanNo;
	
	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "itemCode", nullable = true)
	private Item itemCode;*/
	
	@Column(nullable = true)
	private String prodCode;
	
	@Column(nullable = true)
	private String prodStartdate;
	
	@Column(nullable = true)
	private String prodEnddate;
	
	@Column(nullable = true)
	private String prodQuantity;
	
	@Column(nullable = true)
	private String prodName;
}
