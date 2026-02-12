package com.fulfilment.application.monolith.warehousefullfilment.domain.model;

import java.time.ZonedDateTime;

public class WarehouseFullfilment {
    private String businessUnitCode;
    private String storeName;
    private String productName;
    private ZonedDateTime createdAt;

    public WarehouseFullfilment() {}

    public WarehouseFullfilment(String businessUnitCode, String storeName, String productName, ZonedDateTime createdAt) {
        this.businessUnitCode = businessUnitCode;
        this.storeName = storeName;
        this.productName = productName;
        this.createdAt = createdAt;
    }


    public void setBusinessUnitCode(String businessUnitCode) {
        this.businessUnitCode = businessUnitCode;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getBusinessUnitCode() {
        return businessUnitCode;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getProductName() {
        return productName;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
