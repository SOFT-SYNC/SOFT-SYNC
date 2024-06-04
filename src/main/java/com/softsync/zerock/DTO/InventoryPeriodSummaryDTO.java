package com.softsync.zerock.DTO;

public class InventoryPeriodSummaryDTO {
    private String inventoryCode;
    private String inventoryName;
    private Long totalShipmentQuantity;
    private Long totalReceivingQuantity;
    private Long initialInventory; // 기초재고
    private Long finalInventory; // 기말재고
    private Long finalInventoryValue; // 기말재고금액

    public InventoryPeriodSummaryDTO(String inventoryCode, String inventoryName, Long totalShipmentQuantity, Long totalReceivingQuantity, Long initialInventory, Long finalInventory, Long finalInventoryValue) {
        this.inventoryCode = inventoryCode;
        this.inventoryName = inventoryName;
        this.totalShipmentQuantity = totalShipmentQuantity != null ? totalShipmentQuantity : 0;
        this.totalReceivingQuantity = totalReceivingQuantity != null ? totalReceivingQuantity : 0;
        this.initialInventory = initialInventory != null ? initialInventory : 0;
        this.finalInventory = finalInventory != null ? finalInventory : 0;
        this.finalInventoryValue = finalInventoryValue != null ? finalInventoryValue : 0;
    }
 
    // Getters and Setters
    public String getInventoryCode() {
        return inventoryCode;
    }

    public void setInventoryCode(String inventoryCode) {
        this.inventoryCode = inventoryCode;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public void setInventoryName(String inventoryName) {
        this.inventoryName = inventoryName;
    }

    public Long getTotalShipmentQuantity() {
        return totalShipmentQuantity;
    }

    public void setTotalShipmentQuantity(Long totalShipmentQuantity) {
        this.totalShipmentQuantity = totalShipmentQuantity;
    }

    public Long getTotalReceivingQuantity() {
        return totalReceivingQuantity;
    }

    public void setTotalReceivingQuantity(Long totalReceivingQuantity) {
        this.totalReceivingQuantity = totalReceivingQuantity;
    }

    public Long getInitialInventory() {
        return initialInventory;
    }

    public void setInitialInventory(Long initialInventory) {
        this.initialInventory = initialInventory;
    }

    public Long getFinalInventory() {
        return finalInventory;
    }

    public void setFinalInventory(Long finalInventory) {
        this.finalInventory = finalInventory;
    }

    public Long getFinalInventoryValue() {
        return finalInventoryValue;
    }

    public void setFinalInventoryValue(Long finalInventoryValue) {
        this.finalInventoryValue = finalInventoryValue;
    }
}