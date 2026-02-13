package com.fulfilment.application.monolith.warehousefullfilment.adapters.restapi;

import com.fulfilment.application.monolith.warehousefullfilment.adapters.database.WarehouseFullfilmentRepository;
import com.fulfilment.application.monolith.warehousefullfilment.domain.model.WarehouseFullfilment;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
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
public class WarehouseFullfilmentResourceTest {

    private static final String path = "warehouseFullfilment";

    @Inject
    WarehouseFullfilmentRepository repository;

    @BeforeEach
    @Transactional
    void cleanDb() {
        repository.deleteAll();
    }

    @Test
    public void testAssignWarehouseToProduct() {
        WarehouseFullfilment warehouseFullfilment = new WarehouseFullfilment();
        warehouseFullfilment.setWarehouseId(2L);
        warehouseFullfilment.setStoreId(2L);
        warehouseFullfilment.setProductId(2L);
        warehouseFullfilment.setCreatedAt(ZonedDateTime.now());

        given()
                .contentType("application/json")
                .body(warehouseFullfilment)
                .when().post(path)
                .then()
                .statusCode(200)
                .body("warehouseId", is(2))
                .body("storeId", is(2))
                .body("productId", is(2));
    }

    @Test
    public void testAssignWarehouseToProductWithMissingRequiredField() {
        WarehouseFullfilment warehouseFullfilment = new WarehouseFullfilment();
        // Missing warehouseId which should be @NotNull
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
