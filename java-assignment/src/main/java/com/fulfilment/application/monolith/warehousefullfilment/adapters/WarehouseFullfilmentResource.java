package com.fulfilment.application.monolith.warehousefullfilment.adapters;

import com.fulfilment.application.monolith.warehousefullfilment.domain.model.WarehouseFullfilment;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/warehouseFullfilment")
public interface WarehouseFullfilmentResource {
    @POST
    @Consumes(MediaType.APPLICATION_JSON) // Accept JSON input
    @Produces(MediaType.APPLICATION_JSON)
    WarehouseFullfilment assignWarehouseToProduct(@NotNull WarehouseFullfilment data);
}
