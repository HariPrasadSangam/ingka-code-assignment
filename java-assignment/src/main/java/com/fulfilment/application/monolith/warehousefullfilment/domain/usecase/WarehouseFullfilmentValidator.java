package com.fulfilment.application.monolith.warehousefullfilment.domain.usecase;

import com.fulfilment.application.monolith.products.Product;
import com.fulfilment.application.monolith.products.ProductRepository;
import com.fulfilment.application.monolith.stores.Store;
import com.fulfilment.application.monolith.warehousefullfilment.domain.model.WarehouseFullfilment;
import com.fulfilment.application.monolith.warehousefullfilment.domain.port.WarehouseFullfilmentStore;
import com.fulfilment.application.monolith.warehouses.adapters.database.WarehouseRepository;
import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

@ApplicationScoped
public class WarehouseFullfilmentValidator {
    private static final Logger LOGGER = Logger.getLogger(WarehouseFullfilmentValidator.class);
    private final WarehouseFullfilmentStore warehouseFullfilmentStore;
    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;

    public WarehouseFullfilmentValidator(WarehouseFullfilmentStore warehouseFullfilmentStore, 
                                       WarehouseRepository warehouseRepository,
                                       ProductRepository productRepository) {
        this.warehouseFullfilmentStore = warehouseFullfilmentStore;
        this.warehouseRepository = warehouseRepository;
        this.productRepository = productRepository;
    }

    public void validate(WarehouseFullfilment warehouseFullfilment) {

        validateRequiredFields(warehouseFullfilment);

        // Duplicate assignment
        if (warehouseFullfilmentStore.existsAssignment(warehouseFullfilment)) {
            LOGGER.warnf("Duplicate Fulfilment.");
            throw new IllegalStateException("Duplicate Fulfilment.");
        }

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
        
        if (warehouseFullfilment.getWarehouseId() == null) {
            LOGGER.warn("Warehouse ID is required");
            throw new IllegalArgumentException("Warehouse ID is required");
        }
        
        if (warehouseFullfilment.getStoreId() == null) {
            LOGGER.warn("Store ID is required");
            throw new IllegalArgumentException("Store ID is required");
        }
        
        if (warehouseFullfilment.getProductId() == null) {
            LOGGER.warn("Product ID is required");
            throw new IllegalArgumentException("Product ID is required");
        }

        validateWarehouseAvailability(warehouseFullfilment);
        validateStoreAvailability(warehouseFullfilment);
        validateProductAvailability(warehouseFullfilment);
    }
    
    private void validateWarehouseAvailability(WarehouseFullfilment warehouseFullfilment) {
        Warehouse warehouse = warehouseRepository.findWarehouseById(warehouseFullfilment.getWarehouseId());
        if (warehouse == null) {
            LOGGER.warnf("Warehouse with ID %d does not exist", warehouseFullfilment.getWarehouseId());
            throw new IllegalArgumentException("Warehouse does not exist");
        }
        
        if (warehouse.archivedAt != null) {
            LOGGER.warnf("Warehouse with ID %d is not available (archived)", warehouseFullfilment.getWarehouseId());
            throw new IllegalStateException("Warehouse is not available");
        }
    }
    
    private void validateStoreAvailability(WarehouseFullfilment warehouseFullfilment) {
        Store store = Store.findById(warehouseFullfilment.getStoreId());
        if (store == null) {
            LOGGER.warnf("Store with ID %d does not exist", warehouseFullfilment.getStoreId());
            throw new IllegalArgumentException("Store does not exist");
        }
    }
    
    private void validateProductAvailability(WarehouseFullfilment warehouseFullfilment) {
        Product product = productRepository.findById(warehouseFullfilment.getProductId());
        if (product == null) {
            LOGGER.warnf("Product with ID %d does not exist", warehouseFullfilment.getProductId());
            throw new IllegalArgumentException("Product does not exist");
        }
        
        if (product.stock <= 0) {
            LOGGER.warnf("Product with ID %d is not available (out of stock)", warehouseFullfilment.getProductId());
            throw new IllegalStateException("Product is not available");
        }
    }
}
