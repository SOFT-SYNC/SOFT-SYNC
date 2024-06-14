package com.softsync.zerock.DTO;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcurementPlanDTO {
    private Long id; // 기본키
    private Long productionPlanId; // 생산 계획 ID
    private Long itemId; // 조달 품목 ID
    private int procurementInterval; // 조달 간격(일)
    private int procurementQuantity; // 조달 수량
    private LocalDate procurementDueDate; // 조달 납기일

    // 기본 생성자	
    public ProcurementPlanDTO() {}

    // 모든 필드를 사용하는 생성자
    public ProcurementPlanDTO(Long id, Long productionPlanId, Long itemId, int procurementInterval, int procurementQuantity, LocalDate procurementDueDate) {
        this.id = id;
        this.productionPlanId = productionPlanId;
        this.itemId = itemId;
        this.procurementInterval = procurementInterval;
        this.procurementQuantity = procurementQuantity;
        this.procurementDueDate = procurementDueDate;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductionPlanId() {
		return productionPlanId;
	}

	public void setProductionPlanId(Long productionPlanId) {
		this.productionPlanId = productionPlanId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public int getProcurementInterval() {
		return procurementInterval;
	}

	public void setProcurementInterval(int procurementInterval) {
		this.procurementInterval = procurementInterval;
	}

	public int getProcurementQuantity() {
		return procurementQuantity;
	}

	public void setProcurementQuantity(int procurementQuantity) {
		this.procurementQuantity = procurementQuantity;
	}

	public LocalDate getProcurementDueDate() {
		return procurementDueDate;
	}

	public void setProcurementDueDate(LocalDate procurementDueDate) {
		this.procurementDueDate = procurementDueDate;
	}
    
    
}