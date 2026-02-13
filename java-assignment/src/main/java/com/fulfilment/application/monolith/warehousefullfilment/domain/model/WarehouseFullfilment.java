package com.fulfilment.application.monolith.warehousefullfilment.domain.model;

import java.time.ZonedDateTime;

public class WarehouseFullfilment {
    private Long storeId;
    private Long productId;
    private Long warehouseId;
    private ZonedDateTime createdAt;

    public WarehouseFullfilment() {}

    public WarehouseFullfilment(Long storeId, Long productId, Long warehouseId, ZonedDateTime createdAt) {
        this.storeId = storeId;
        this.productId = productId;
        this.warehouseId = warehouseId;
        this.createdAt = createdAt;
    }


    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getStoreId() {
        return storeId;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
