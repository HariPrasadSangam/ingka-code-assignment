package com.fulfilment.application.monolith.warehousefullfilment.domain.port;

import com.fulfilment.application.monolith.warehousefullfilment.domain.model.WarehouseFullfilment;

public interface WarehouseAsFullfilmentUnitsOperation {
    void createWarehouseFullfilment(WarehouseFullfilment warehouseFullfilment);
}
