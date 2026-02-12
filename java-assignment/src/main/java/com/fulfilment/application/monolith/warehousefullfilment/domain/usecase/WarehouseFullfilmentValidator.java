package com.fulfilment.application.monolith.warehousefullfilment.domain.usecase;

import com.fulfilment.application.monolith.warehousefullfilment.domain.model.WarehouseFullfilment;
import com.fulfilment.application.monolith.warehousefullfilment.domain.port.WarehouseFullfilmentStore;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

@ApplicationScoped
public class WarehouseFullfilmentValidator {
    private static final Logger LOGGER = Logger.getLogger(WarehouseFullfilmentValidator.class);
    private final WarehouseFullfilmentStore warehouseFullfilmentStore;

    public WarehouseFullfilmentValidator(WarehouseFullfilmentStore warehouseFullfilmentStore) {
        this.warehouseFullfilmentStore = warehouseFullfilmentStore;
    }

    public void validate(WarehouseFullfilment warehouseFullfilment) {

        validateRequiredFields(warehouseFullfilment);

        // max 2 warehouses per product per store
        long warehousesPerProduct = warehouseFullfilmentStore.findNumberofWarehousesForAProductPerStore(warehouseFullfilment);
        if (warehousesPerProduct > 2) {
            LOGGER.warnf("A product can be fulfilled by max 2 warehouses per store");
            throw new IllegalStateException("A product can be fulfilled by max 2 warehouses per store.");
        }
        //  max 3 warehouses per store
        long warehousePerStore = warehouseFullfilmentStore.findNumberofWarehousesPerStore(warehouseFullfilment);
        if (warehousePerStore > 3) {
            LOGGER.warnf("A store can be fulfilled by max 3 warehouses.");
            throw new IllegalStateException("A store can be fulfilled by max 3 warehouses.");
        }
        //max 5 products per warehouse
        long warehousePerProduct = warehouseFullfilmentStore.findNumberofWarehousesPerproduct(warehouseFullfilment);
        if (warehousePerProduct > 5) {
            LOGGER.warnf("A warehouse can fulfil max 5 product types.");
            throw new IllegalStateException("A warehouse can fulfil max 5 product types.");
        }

    }

    public void validateRequiredFields(WarehouseFullfilment warehouseFullfilment) {
        if (warehouseFullfilment == null) {
            LOGGER.warn("WarehouseFullfilment cannot be null");
            throw new IllegalArgumentException("WarehouseFullfilment cannot be null");
        }
        
        if (warehouseFullfilment.getBusinessUnitCode() == null || warehouseFullfilment.getBusinessUnitCode().trim().isEmpty()) {
            LOGGER.warn("Business unit code is required");
            throw new IllegalArgumentException("Business unit code is required");
        }
        
        if (warehouseFullfilment.getStoreName() == null || warehouseFullfilment.getStoreName().trim().isEmpty()) {
            LOGGER.warn("Store name is required");
            throw new IllegalArgumentException("Store name is required");
        }
        
        if (warehouseFullfilment.getProductName() == null || warehouseFullfilment.getProductName().trim().isEmpty()) {
            LOGGER.warn("Product name is required");
            throw new IllegalArgumentException("Product name is required");
        }
    }
}
