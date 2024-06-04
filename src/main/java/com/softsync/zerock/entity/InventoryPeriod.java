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
@Table(name = "inventory_periods")
public class InventoryPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본키

    @ManyToOne(fetch = FetchType.EAGER) // eager 로딩으로 수정
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory; // 재고

    @ManyToOne(fetch = FetchType.EAGER) // eager 로딩으로 수정
    @JoinColumn(name = "shipment_list_id")
    private ShipmentList shipmentList; // 출고 목록

    @ManyToOne(fetch = FetchType.EAGER) // eager 로딩으로 수정
    @JoinColumn(name = "receiving_list_id")
    private ReceiveList receiveList; // 입고 목록

    @Column(nullable = false)
    private LocalDate date; // 기록일
  
    // Getter and Setter 생략
}

