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
        firstAssignment.setBusinessUnitCode("MWH.001");
        firstAssignment.setStoreName("AMSTERDAM-001");
        firstAssignment.setProductName("DESKTOP-005");
        firstAssignment.setCreatedAt(ZonedDateTime.now());

        given()
                .contentType("application/json")
                .body(firstAssignment)
                .when().post(path)
                .then()
                .statusCode(200)
                .body("businessUnitCode", is("MWH.001"));

        // Second assignment
        WarehouseFullfilment secondAssignment = new WarehouseFullfilment();
        secondAssignment.setBusinessUnitCode("MWH.012");
        secondAssignment.setStoreName("ZWOLLE-001");
        secondAssignment.setProductName("TABLET-006");
        secondAssignment.setCreatedAt(ZonedDateTime.now());

        given()
                .contentType("application/json")
                .body(secondAssignment)
                .when().post(path)
                .then()
                .statusCode(200)
                .body("businessUnitCode", is("MWH.012"));
    }

    @Test
    public void testAssignWarehouseToProductWithInvalidData() {
        WarehouseFullfilment warehouseFullfilment = new WarehouseFullfilment();
        // Missing required businessUnitCode
        warehouseFullfilment.setStoreName("INVALID-STORE");
        warehouseFullfilment.setProductName("INVALID-PRODUCT");
        warehouseFullfilment.setCreatedAt(ZonedDateTime.now());

        given()
                .contentType("application/json")
                .body(warehouseFullfilment)
                .when().post(path)
                .then()
                .statusCode(400);
    }
}
