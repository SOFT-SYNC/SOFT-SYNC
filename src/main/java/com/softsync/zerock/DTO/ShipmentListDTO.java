package com.softsync.zerock.DTO;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShipmentListDTO {
    private Long shipmentId; // Shipment ID
    private Integer quantity; // 출고 수량
    private LocalDate shipmentDate; // 출고 날짜

    // 기본 생성자
    public ShipmentListDTO() {
    }

    // 모든 필드를 갖는 생성자
    public ShipmentListDTO(Long shipmentId, Integer quantity, LocalDate shipmentDate) {
        this.shipmentId = shipmentId;
        this.quantity = quantity;
        this.shipmentDate = shipmentDate;
    }
    
    // Getters and setters...
}
