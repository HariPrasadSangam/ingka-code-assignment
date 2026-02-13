package com.fulfilment.application.monolith.warehousefullfilment.adapters.database;

import com.fulfilment.application.monolith.warehousefullfilment.domain.model.WarehouseFullfilment;
import com.fulfilment.application.monolith.warehousefullfilment.domain.port.WarehouseFullfilmentStore;
import com.fulfilment.application.monolith.warehouses.adapters.database.DbWarehouse;
import edu.umd.cs.findbugs.annotations.NonNull;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;

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

    public boolean existsAssignment(WarehouseFullfilment warehouseFullfilment) {
        return count("storeId = ?1 and productId = ?2 and warehouseId = ?3", warehouseFullfilment.getStoreId(), warehouseFullfilment.getProductId(), warehouseFullfilment.getWarehouseId()) > 0;
    }

    @Override
    public long findNumberofWarehousesForAProductPerStore(WarehouseFullfilment warehouseFullfilment) {
        return getEntityManager().createQuery(
                "SELECT COUNT(DISTINCT wf.warehouseId) " +
                        "FROM DbWarehouseFullFilment wf " +
                        "WHERE wf.productId = :productId AND wf.storeId = :storeId", Long.class)
                .setParameter("productId", warehouseFullfilment.getProductId())
                .setParameter("storeId", warehouseFullfilment.getStoreId())
                .getSingleResult();
    }

    @Override
    public long findNumberofWarehousesPerStore(WarehouseFullfilment warehouseFullfilment) {
        return getEntityManager().createQuery(
                "SELECT COUNT(DISTINCT wf.warehouseId) " +
                        "FROM DbWarehouseFullFilment wf " +
                        "WHERE wf.storeId = :storeId", Long.class)
                .setParameter("storeId", warehouseFullfilment.getStoreId())
                .getSingleResult();
    }

    @Override
    public long findNumberofWarehousesPerproduct(WarehouseFullfilment warehouseFullfilment) {
        return getEntityManager().createQuery(
                "SELECT COUNT(DISTINCT wf.productId) " +
                        "FROM DbWarehouseFullFilment wf " +
                        "WHERE wf.warehouseId = :warehouseId", Long.class)
                .setParameter("warehouseId", warehouseFullfilment.getWarehouseId())
                .getSingleResult();
    }

    private DbWarehouseFullFilment entityFromWarehouseFullFilment(WarehouseFullfilment warehouseFullfilment) {
        DbWarehouseFullFilment entity = new DbWarehouseFullFilment();
        entity.setWarehouseId(warehouseFullfilment.getWarehouseId());
        entity.setStoreId(warehouseFullfilment.getStoreId());
        entity.setProductId(warehouseFullfilment.getProductId());
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
