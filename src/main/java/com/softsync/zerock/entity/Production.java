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
@Table(name = "production")
@DynamicUpdate
public class Production {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer prodPlanNo;
	
	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "itemCode", nullable = true)
	private Item itemCode;*/
	
	@Column(nullable = false,name = "prod_code")
	private String prodCode;
	
	@Column(nullable = true,name = "prod_startdate")
	private String prodStartdate;
	
	@Column(nullable = true,name = "prod_enddate")
	private String prodEnddate;
	
	@Column(nullable = true,name = "prod_quantity")
	private String prodQuantity;
	
	@Column(nullable = true,name = "prod_name")
	private String prodName;
}
