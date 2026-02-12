package com.fulfilment.application.monolith.warehousefullfilment.adapters.restapi;

import com.fulfilment.application.monolith.warehousefullfilment.domain.model.WarehouseFullfilment;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.ZonedDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
@TestTransaction
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WarehouseFullfilmentResourceTest {

    private static final String path = "warehouseFullfilment";

    @Test
    @Order(1)
    public void testAssignWarehouseToProduct() {
        WarehouseFullfilment warehouseFullfilment = new WarehouseFullfilment();
        warehouseFullfilment.setBusinessUnitCode("MWH.001");
        warehouseFullfilment.setStoreName("AMSTERDAM-001");
        warehouseFullfilment.setProductName("LAPTOP-001");
        warehouseFullfilment.setCreatedAt(ZonedDateTime.now());

        given()
                .contentType("application/json")
                .body(warehouseFullfilment)
                .when().post(path)
                .then()
                .statusCode(200)
                .body("businessUnitCode", is("MWH.001"))
                .body("storeName", is("AMSTERDAM-001"))
                .body("productName", is("LAPTOP-001"))
                .body("createdAt", notNullValue());
    }

    @Test
    @Order(2)
    public void testAssignWarehouseToProductWithDifferentData() {
        WarehouseFullfilment warehouseFullfilment = new WarehouseFullfilment();
        warehouseFullfilment.setBusinessUnitCode("MWH.012");
        warehouseFullfilment.setStoreName("ZWOLLE-001");
        warehouseFullfilment.setProductName("MONITOR-002");
        warehouseFullfilment.setCreatedAt(ZonedDateTime.now());

        given()
                .contentType("application/json")
                .body(warehouseFullfilment)
                .when().post(path)
                .then()
                .statusCode(200)
                .body("businessUnitCode", is("MWH.012"))
                .body("storeName", is("ZWOLLE-001"))
                .body("productName", is("MONITOR-002"));
    }

    @Test
    @Order(3)
    public void testAssignWarehouseToProductWithMissingRequiredField() {
        WarehouseFullfilment warehouseFullfilment = new WarehouseFullfilment();
        // Missing businessUnitCode which should be @NotNull
        warehouseFullfilment.setStoreName("TILBURG-001");
        warehouseFullfilment.setProductName("KEYBOARD-003");
        warehouseFullfilment.setCreatedAt(ZonedDateTime.now());

        given()
                .contentType("application/json")
                .body(warehouseFullfilment)
                .when().post(path)
                .then()
                .statusCode(400);
    }

}
