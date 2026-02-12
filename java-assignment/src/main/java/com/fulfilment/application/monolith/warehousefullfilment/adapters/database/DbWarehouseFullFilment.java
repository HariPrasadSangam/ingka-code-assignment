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

    private String store;
    private String product;
    private String warehouse;
    private LocalDateTime createdAt;

    public DbWarehouseFullFilment(){}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStore() {
        return store;
    }

    public String getProduct() {
        return product;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
