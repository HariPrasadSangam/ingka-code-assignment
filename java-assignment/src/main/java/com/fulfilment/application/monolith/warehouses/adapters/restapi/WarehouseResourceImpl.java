package com.fulfilment.application.monolith.warehouses.adapters.restapi;

import com.fulfilment.application.monolith.warehouses.adapters.database.WarehouseRepository;
import com.fulfilment.application.monolith.warehouses.domain.ports.ArchiveWarehouseOperation;
import com.fulfilment.application.monolith.warehouses.domain.ports.CreateWarehouseOperation;
import com.fulfilment.application.monolith.warehouses.domain.ports.ReplaceWarehouseOperation;
import com.warehouse.api.WarehouseResource;
import com.warehouse.api.beans.Warehouse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.WebApplicationException;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class WarehouseResourceImpl implements WarehouseResource {

  private static final Logger LOGGER = Logger.getLogger(WarehouseResourceImpl.class);

  @Inject WarehouseRepository warehouseRepository;

  @Inject CreateWarehouseOperation createWarehouseOperation;

  @Inject ArchiveWarehouseOperation archiveWarehouseOperation;

  @Inject ReplaceWarehouseOperation replaceWarehouseOperation;

  @Override
  public List<Warehouse> listAllWarehousesUnits() {
    return warehouseRepository.getAll().stream().map(this::toWarehouseResponse).toList();
  }

  @Override
  @Transactional
  public Warehouse createANewWarehouseUnit(@NotNull Warehouse data) {
    LOGGER.infof("Creating a new warehouse unit: %s", data.getBusinessUnitCode());
    com.fulfilment.application.monolith.warehouses.domain.models.Warehouse domain = toDomainWarehouse(data);
    createWarehouseOperation.create(domain);
    // return what was stored/created
    return toWarehouseResponse(domain);
  }

  @Override
  public Warehouse getAWarehouseUnitByID(String id) {
    LOGGER.infof("Getting warehouse unit by ID: %s", id);
    if (StringUtils.isEmpty(id)) {
      throw new IllegalArgumentException("Warehouse Id cannot be empty");
    }
    Long warehouseId = Long.parseLong(id);
    var warehouse = warehouseRepository.findWarehouseById(warehouseId);
    if (warehouse == null || warehouse.archivedAt != null) {
      throw new WebApplicationException("Warehouse not found: " + id, 404);
    }
    return toWarehouseResponse(warehouse);
  }

  @Override
  @Transactional
  public void archiveAWarehouseUnitByID(String id) {
    LOGGER.infof("Archiving warehouse unit by ID: %s", id);
    if (StringUtils.isEmpty(id)) {
      throw new IllegalArgumentException("Warehouse Id cannot be empty");
    }
    Long warehouseId = Long.parseLong(id);
    var warehouse = warehouseRepository.findWarehouseById(warehouseId);
    if (warehouse == null || warehouse.archivedAt != null) {
      throw new WebApplicationException("Active warehouse not found: " + id, 404);
    }
    archiveWarehouseOperation.archive(warehouse);
  }

  @Override
  @Transactional
  public Warehouse replaceTheCurrentActiveWarehouse(
          String businessUnitCode, @NotNull Warehouse data) {
    LOGGER.infof("Replacing warehouse : %s", businessUnitCode);
    if (StringUtils.isEmpty(businessUnitCode)) {
      throw new IllegalArgumentException("Warehouse businessUnitCode cannot be empty");
    }
    com.fulfilment.application.monolith.warehouses.domain.models.Warehouse domain = toDomainWarehouse(data);
    // enforce path param as the identifier to replace
    domain.businessUnitCode = businessUnitCode;
    replaceWarehouseOperation.replace(domain);
    // return updated state from DB (source of truth)
    var updated = warehouseRepository.findByBusinessUnitCode(domain.businessUnitCode);
    if (updated == null || updated.archivedAt != null) {
      throw new WebApplicationException("Warehouse not found after replacement.", 500);
    }
    return toWarehouseResponse(updated);
  }

  private Warehouse toWarehouseResponse(
          com.fulfilment.application.monolith.warehouses.domain.models.Warehouse warehouse) {

    var response = new Warehouse();
    response.setId(warehouse.id != null ? warehouse.id.toString() : null);
    response.setBusinessUnitCode(warehouse.businessUnitCode);
    response.setLocation(warehouse.location);
    response.setCapacity(warehouse.capacity);
    response.setStock(warehouse.stock);
    return response;
  }

  private com.fulfilment.application.monolith.warehouses.domain.models.Warehouse toDomainWarehouse(Warehouse data) {
    if (data == null) {
      throw new WebApplicationException("Request body was not set.", 422);
    }
    var w = new com.fulfilment.application.monolith.warehouses.domain.models.Warehouse();
    w.businessUnitCode = data.getBusinessUnitCode();
    w.location = data.getLocation();
    w.capacity = data.getCapacity();
    w.stock = data.getStock();
    return w;
  }

}
