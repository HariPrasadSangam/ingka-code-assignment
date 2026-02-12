package com.fulfilment.application.monolith.warehousefullfilment.adapters.restapi;

import com.fulfilment.application.monolith.warehousefullfilment.adapters.WarehouseFullfilmentResource;
import com.fulfilment.application.monolith.warehousefullfilment.domain.model.WarehouseFullfilment;
import com.fulfilment.application.monolith.warehousefullfilment.domain.usecase.WarehouseAsFullfilmentUnitsUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class WarehouseFullfilmentResourceImpl implements WarehouseFullfilmentResource {

    private final WarehouseAsFullfilmentUnitsUseCase warehouseAsFullfilmentUnitsUseCase;

    public WarehouseFullfilmentResourceImpl(WarehouseAsFullfilmentUnitsUseCase
                                                    warehouseAsFullfilmentUnitsUseCase) {
        this.warehouseAsFullfilmentUnitsUseCase = warehouseAsFullfilmentUnitsUseCase;
    }
    @Override
    @Transactional
    public WarehouseFullfilment assignWarehouseToProduct(@NotNull WarehouseFullfilment data) {
        warehouseAsFullfilmentUnitsUseCase.createWarehouseFullfilment(data);
        return data;
    }
}
