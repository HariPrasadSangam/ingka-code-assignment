package com.fulfilment.application.monolith.warehouses.domain.usecases;

import com.fulfilment.application.monolith.warehouses.domain.models.Location;
import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.LocationResolver;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class WarehouseValidator {
    private static final Logger LOGGER = Logger.getLogger(WarehouseValidator.class);
    private final WarehouseStore warehouseStore;
    private final LocationResolver locationResolver;

    public WarehouseValidator(WarehouseStore warehouseStore, LocationResolver locationResolver) {
        this.warehouseStore = warehouseStore;
        this.locationResolver = locationResolver;
    }

    public void validate(Warehouse warehouse, Warehouse existingWarehouse) {

        validateRequiredFields(warehouse);

        // Location Validation
        Location location = locationResolver.resolveByIdentifier(warehouse.location);
        if (location == null) {
            LOGGER.warnf("Invalid location: %s", warehouse.location);
            throw new IllegalArgumentException("Invalid location");
        }
        if (existingWarehouse != null) {
            validateNotArchived(existingWarehouse);
        }
        List<Warehouse> existingWarehouses = warehouseStore.findByLocation(warehouse.location);
        // Warehouse Creation Feasibility
        // If it's a new warehouse OR the location is changing during replacement
        if (existingWarehouse == null || !warehouse.location.equals(existingWarehouse.location)) {
            long activeWarehousesCount =
                    existingWarehouses.stream().filter(w -> w.archivedAt == null).count();

            if (activeWarehousesCount >= location.maxNumberOfWarehouses) {
                LOGGER.warnf("Maximum number of warehouses reached for location: %s", warehouse.location);
                throw new IllegalStateException("Maximum number of warehouses reached for this location.");
            }
        }

        // Capacity and Stock Validation
        int currentTotalCapacity =
                existingWarehouses.stream()
                        .filter(wh -> wh.archivedAt == null)
                        .filter(wh -> existingWarehouse == null || !wh.businessUnitCode.equals(existingWarehouse.businessUnitCode))
                        .mapToInt(wh -> wh.capacity)
                        .sum();

        if (currentTotalCapacity + warehouse.capacity > location.maxCapacity) {
            LOGGER.warnf("Maximum capacity reached for location: %s", warehouse.location);
            throw new IllegalStateException("Maximum capacity reached for this location");
        }

        if (warehouse.stock > warehouse.capacity) {
            LOGGER.warnf("Stock exceeds capacity for warehouse: %s", warehouse.businessUnitCode);
            throw new IllegalStateException("Stock exceeds warehouse capacity");
        }

        if (existingWarehouse != null) {
            // Capacity Accommodation
            if (warehouse.capacity < existingWarehouse.stock) {
                LOGGER.warnf("New capacity cannot accommodate old stock for warehouse: %s", warehouse.businessUnitCode);
                throw new IllegalStateException("New capacity cannot accommodate old stock");
            }

            // Stock Matching
            if (!warehouse.stock.equals(existingWarehouse.stock)) {
                LOGGER.warnf("Stock mismatch for the warehouse: %s", warehouse.businessUnitCode);
                throw new IllegalStateException("Stock mismatch for the warehouse");
            }
        }
    }

    public void validateRequiredFields(Warehouse warehouse) {
        if (warehouse == null) {
            throw new IllegalArgumentException("Invalid Request");
        }
        if (warehouse.businessUnitCode == null || warehouse.businessUnitCode.isBlank()) {
            throw new IllegalArgumentException("Invalid businessUnitCode.");
        }
        if (warehouse.capacity == null || warehouse.capacity <= 0) {
            throw new IllegalArgumentException("Invalid Warehouse capacity");
        }
        if (warehouse.stock == null || warehouse.stock < 0) {
            throw new IllegalArgumentException("Invalid Warehouse stock.");
        }
    }

    public void validateNotArchived(Warehouse warehouse) {
        if (warehouse.archivedAt != null) {
            LOGGER.warnf("Warehouse already archived: %s", warehouse.businessUnitCode);
            throw new IllegalStateException("Warehouse is already archived");
        }
    }
}
