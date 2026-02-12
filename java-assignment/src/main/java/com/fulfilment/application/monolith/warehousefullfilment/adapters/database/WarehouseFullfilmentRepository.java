package com.fulfilment.application.monolith.warehousefullfilment.adapters.database;

import com.fulfilment.application.monolith.warehousefullfilment.domain.model.WarehouseFullfilment;
import com.fulfilment.application.monolith.warehousefullfilment.domain.port.WarehouseFullfilmentStore;
import com.fulfilment.application.monolith.warehouses.adapters.database.DbWarehouse;
import edu.umd.cs.findbugs.annotations.NonNull;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@ApplicationScoped
public class WarehouseFullfilmentRepository implements WarehouseFullfilmentStore, PanacheRepository<DbWarehouseFullFilment> {
    @Override
    public void create(WarehouseFullfilment warehouseFullfilment) {
        warehouseFullfilment.setCreatedAt(getZonedDateTime());
       persist(entityFromWarehouseFullFilment(warehouseFullfilment));
    }

    @NonNull
    private static ZonedDateTime getZonedDateTime() {
        return LocalDateTime.now().atZone(ZoneOffset.UTC);
    }

    @Override
    public long findNumberofWarehousesForAProductPerStore(WarehouseFullfilment warehouseFullfilment) {
        return  find(
                "SELECT COUNT(wf) " +
                        "FROM DbWarehouseFullFilment wf " +
                        "WHERE wf.product = ?1 AND wf.store =?2",
                warehouseFullfilment.getProductName(), warehouseFullfilment.getStoreName()
        ).project(Long.class).firstResult();
    }

    @Override
    public long findNumberofWarehousesPerStore(WarehouseFullfilment warehouseFullfilment) {
        return  find(
                "SELECT COUNT(DISTINCT wf.warehouse) " +
                        "FROM DbWarehouseFullFilment wf " +
                        "WHERE wf.store = ?1",
                warehouseFullfilment.getStoreName()
        ).project(Long.class).firstResult();
    }

    @Override
    public long findNumberofWarehousesPerproduct(WarehouseFullfilment warehouseFullfilment) {
        return   find(
                "SELECT COUNT(DISTINCT wf.product) " +
                        "FROM DbWarehouseFullFilment wf " +
                        "WHERE wf.warehouse = ?1",
                warehouseFullfilment.getBusinessUnitCode()
        ).project(Long.class).firstResult();
    }

    private DbWarehouseFullFilment entityFromWarehouseFullFilment(WarehouseFullfilment warehouseFullfilment) {
        DbWarehouseFullFilment entity = new DbWarehouseFullFilment();
        entity.setWarehouse(warehouseFullfilment.getBusinessUnitCode());
        entity.setStore(warehouseFullfilment.getStoreName());
        entity.setProduct(warehouseFullfilment.getProductName());
        entity.setCreatedAt(getLocalDate(warehouseFullfilment.getCreatedAt()));
        return entity;
    }

    private LocalDateTime getLocalDate(ZonedDateTime createdOrArchivedAt) {
        if (createdOrArchivedAt != null) {
            return createdOrArchivedAt.toLocalDateTime();
        }
        return null;
    }
}
