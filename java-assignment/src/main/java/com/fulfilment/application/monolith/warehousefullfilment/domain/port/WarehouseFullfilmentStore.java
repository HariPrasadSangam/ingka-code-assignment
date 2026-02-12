package com.fulfilment.application.monolith.warehousefullfilment.domain.port;

import com.fulfilment.application.monolith.warehousefullfilment.domain.model.WarehouseFullfilment;

public interface WarehouseFullfilmentStore {
    void create(WarehouseFullfilment warehouseFullfilment);
    long findNumberofWarehousesForAProductPerStore(WarehouseFullfilment warehouseFullfilment);
    long findNumberofWarehousesPerStore(WarehouseFullfilment warehouseFullfilment);
    long findNumberofWarehousesPerproduct(WarehouseFullfilment warehouseFullfilment);
}
