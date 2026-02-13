package com.fulfilment.application.monolith.warehousefullfilment.adapters.database;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "warehousefullfilment")
public class DbWarehouseFullFilment {
    @Id
    @GeneratedValue
    private Long id;

    private Long storeId;
    private Long productId;
    private Long warehouseId;
    private LocalDateTime createdAt;

    public DbWarehouseFullFilment(){}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setCreatedAt(LocalDateTime createdAt) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
