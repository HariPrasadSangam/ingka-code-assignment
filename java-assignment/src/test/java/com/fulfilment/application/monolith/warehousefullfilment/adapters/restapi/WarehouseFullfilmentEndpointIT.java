package com.fulfilment.application.monolith.warehousefullfilment.adapters.restapi;

import com.fulfilment.application.monolith.warehousefullfilment.domain.model.WarehouseFullfilment;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusIntegrationTest
public class WarehouseFullfilmentEndpointIT {

    private static final String path = "warehouseFullfilment";

    @Test
    public void testAssignMultipleWarehousesToProducts() {
        // First assignment
        WarehouseFullfilment firstAssignment = new WarehouseFullfilment();
        firstAssignment.setWarehouseId(1L);
        firstAssignment.setStoreId(1L);
        firstAssignment.setProductId(1L);
        firstAssignment.setCreatedAt(ZonedDateTime.now());

        given()
                .contentType("application/json")
                .body(firstAssignment)
                .when().post(path)
                .then()
                .statusCode(200)
                .body("warehouseId", is(1));

        // Second assignment
        WarehouseFullfilment secondAssignment = new WarehouseFullfilment();
        secondAssignment.setWarehouseId(2L);
        secondAssignment.setStoreId(2L);
        secondAssignment.setProductId(2L);
        secondAssignment.setCreatedAt(ZonedDateTime.now());

        given()
                .contentType("application/json")
                .body(secondAssignment)
                .when().post(path)
                .then()
                .statusCode(200)
                .body("warehouseId", is(2));
    }

    @Test
    public void testAssignWarehouseToProductWithInvalidData() {
        WarehouseFullfilment warehouseFullfilment = new WarehouseFullfilment();
        // Missing required warehouseId
        warehouseFullfilment.setStoreId(3L);
        warehouseFullfilment.setProductId(3L);
        warehouseFullfilment.setCreatedAt(ZonedDateTime.now());

        given()
                .contentType("application/json")
                .body(warehouseFullfilment)
                .when().post(path)
                .then()
                .statusCode(400);
    }
}
