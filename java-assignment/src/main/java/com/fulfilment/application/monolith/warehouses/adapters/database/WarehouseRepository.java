package com.fulfilment.application.monolith.warehouses.adapters.database;

import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class WarehouseRepository implements WarehouseStore, PanacheRepository<DbWarehouse> {

  private static final Logger LOGGER = Logger.getLogger(WarehouseRepository.class);

  @Override
  public List<Warehouse> getAll() {
    LOGGER.debug("Get all warehouses");
    return list("archivedAt is null").stream().map(DbWarehouse::toWarehouse).collect(Collectors.toList());
  }

  @Override
  public void create(Warehouse warehouse) {
    LOGGER.infof("Creating warehouse : %s", warehouse.businessUnitCode);
    warehouse.createdAt = LocalDateTime.now();
    DbWarehouse dbWarehouse = DbWarehouse.fromWarehouse(warehouse);
    this.persist(dbWarehouse);
  }

  @Override
  public void update(Warehouse warehouse) {
    LOGGER.infof("Updating warehouse : %s", warehouse.businessUnitCode);
    DbWarehouse dbWarehouse = null;
    if (warehouse.id != null) {
      dbWarehouse = findById(warehouse.id);
    } else if (warehouse.businessUnitCode != null) {
      dbWarehouse = find("businessUnitCode", warehouse.businessUnitCode).firstResult();
    }

    if (dbWarehouse != null) {
      dbWarehouse.location = warehouse.location;
      dbWarehouse.capacity = warehouse.capacity;
      dbWarehouse.stock = warehouse.stock;
      dbWarehouse.archivedAt = warehouse.archivedAt;
      this.persist(dbWarehouse);
    } else {
      LOGGER.warnf("Warehouse not found for update: %s", warehouse.businessUnitCode);
    }
  }

  @Override
  public void remove(Warehouse warehouse) {
    LOGGER.infof("Removing warehouse : %s", warehouse.businessUnitCode);
    // Remove only the ACTIVE record (do not delete history by accident)
    DbWarehouse entity =
            this.find("businessUnitCode = ?1 and archivedAt is null", warehouse.businessUnitCode)
                    .firstResult();

    if (entity != null) {
      warehouse.archivedAt = LocalDateTime.now();
      this.update(warehouse);
    }
  }

  @Override
  public Warehouse findByBusinessUnitCode(String buCode) {
    LOGGER.debugf("Finding warehouse by business unit code: %s", buCode);
    if (buCode == null || buCode.isBlank()) {
      return null;
    }
    DbWarehouse entity = this.find("businessUnitCode = ?1 and archivedAt is null", buCode.trim()).firstResult();
    return entity == null ? null : entity.toWarehouse();
  }

  public DbWarehouse findActiveDbByBusinessUnitCode(String buCode) {
    if (buCode == null || buCode.isBlank()) {
      return null;
    }
    return find("businessUnitCode = ?1 and archivedAt is null", buCode.trim()).firstResult();
  }

  @Override
  public Warehouse findWarehouseById(Long id) {
    LOGGER.debugf("Finding warehouses by id: %s", id);
    DbWarehouse dbWarehouse = find("id", id).firstResult();
    return dbWarehouse != null ? dbWarehouse.toWarehouse() : null;
  }

  @Override
  public List<Warehouse> findByLocation(String locationIdentifier) {
    LOGGER.debugf("Finding warehouses by location: %s", locationIdentifier);
    return find("location = ?1 and archivedAt is null", locationIdentifier).stream().map(DbWarehouse::toWarehouse).collect(Collectors.toList());
  }
}
