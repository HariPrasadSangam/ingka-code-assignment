package com.fulfilment.application.monolith.warehousefullfilment.domain.usecase;

import com.fulfilment.application.monolith.warehousefullfilment.domain.model.WarehouseFullfilment;
import com.fulfilment.application.monolith.warehousefullfilment.domain.port.WarehouseAsFullfilmentUnitsOperation;
import com.fulfilment.application.monolith.warehousefullfilment.domain.port.WarehouseFullfilmentStore;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;


@ApplicationScoped
public class WarehouseAsFullfilmentUnitsUseCase implements
        WarehouseAsFullfilmentUnitsOperation {
    private static final Logger LOGGER = Logger.getLogger(WarehouseAsFullfilmentUnitsUseCase.class);
    private final WarehouseFullfilmentStore warehouseFullfilmentStore;
    private final WarehouseFullfilmentValidator warehouseFullfilmentValidator;

    public WarehouseAsFullfilmentUnitsUseCase(WarehouseFullfilmentStore warehouseFullfilmentStore, WarehouseFullfilmentValidator warehouseFullfilmentValidator) {
        this.warehouseFullfilmentStore = warehouseFullfilmentStore;
        this.warehouseFullfilmentValidator = warehouseFullfilmentValidator;
    }

    @Override
    public void createWarehouseFullfilment(WarehouseFullfilment warehouseFullfilment) {
        LOGGER.infof("Creating Warehouse Fullfilment Store: %s", warehouseFullfilment.getWarehouseId());
        warehouseFullfilmentValidator.validate(warehouseFullfilment);
        warehouseFullfilmentStore.create(warehouseFullfilment);
        LOGGER.infof("Created Warehouse Fullfilment Store: %s", warehouseFullfilment.getWarehouseId());
    }
}
