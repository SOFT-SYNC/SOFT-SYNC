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
@Table(name = "shipments_list")
public class ShipmentList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본키

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipment_id", nullable = false)
    private Shipment shipment; // Shipment 엔티티와의 외래키 관계

    @Column(nullable = false)
    private Integer quantity; // 출고 수량

    @Column(nullable = false)
    private Integer inventoryQuantity; // 출고 시점의 재고 수량

    @Column(nullable = false)
    private LocalDate shipmentDate; // 출고 날짜

    // 기본 생성자
    public ShipmentList() {
    }

    // Long 타입의 생성자 추가
    public ShipmentList(Long id) {
        this.id = id;
    }

    // Getters and setters...
    
    
}
