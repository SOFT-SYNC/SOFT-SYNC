package com.softsync.zerock.entity;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "orders")
@DynamicUpdate
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Orders {

	@Id
	private String orderNo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id", nullable = true)
	private User employeeId;

	@ManyToOne
	@JoinColumn(name = "brn")
	private Company company;

	@ManyToOne
	private Item item;

	@ManyToOne(fetch = FetchType.LAZY)
	private Contract contract;

	@OneToMany(mappedBy = "orders", fetch = FetchType.LAZY)
	private List<Reciving> Receivings; // 입고 1:n

	@Column(nullable = false)
	private Integer orderQuantity;

	@Column(nullable = false)
	private LocalDate orderDate;

	@Column(nullable = false)
	private LocalDate receiveDuedate;

	@Column(nullable = true, length = 1, columnDefinition = "CHAR(1) DEFAULT 'N'")
	private String orderYn;

	@Column(nullable = true, length = 1, columnDefinition = "CHAR(1) DEFAULT 'N'")
	private String receiptYn;

	@Column(nullable = true)
	private String orderNote;
}