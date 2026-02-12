package com.fulfilment.application.monolith.stores;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
public class StoreResourceTest {

    @Test
    public void testGetAllStores() {
        given()
                .when().get("/store")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void testCreateStore() {
        String storeJson = """
            {
              "name": "Test Store",
              "quantityProductsInStock": 10
            }
            """;

        given()
                .contentType(ContentType.JSON)
                .body(storeJson)
                .when().post("/store")
                .then()
                .statusCode(201)
                .body("name", is("Test Store"))
                .body("quantityProductsInStock", is(10));
    }

    @Test
    public void testGetSingleStore() {
        String storeJson = """
            {
              "name": "Single Store",
              "quantityProductsInStock": 5
            }
            """;


        Integer storeIdInt = given()
                .contentType(ContentType.JSON)
                .body(storeJson)
                .when().post("/store")
                .then()
                .statusCode(201)
                .extract()
                .path("id");
        Long storeId = storeIdInt.longValue();

        given()
                .when().get("/store/" + storeId)
                .then()
                .statusCode(200)
                .body("name", is("Single Store"))
                .body("quantityProductsInStock", is(5));
    }

    @Test
    public void testUpdateStore() {
        String storeJson = """
            {
              "name": "Update Store",
              "quantityProductsInStock": 15
            }
            """;

        Integer storeIdInt =
                given()
                        .contentType(ContentType.JSON)
                        .body(storeJson)
                        .when().post("/store")
                        .then()
                        .statusCode(201)
                        .extract()
                        .path("id");
        Long storeId = storeIdInt.longValue();

        String updatedJson = """
            {
              "name": "Updated Store",
              "quantityProductsInStock": 20
            }
            """;

        given()
                .contentType(ContentType.JSON)
                .body(updatedJson)
                .when().put("/store/" + storeId)
                .then()
                .statusCode(200)
                .body("name", is("Updated Store"))
                .body("quantityProductsInStock", is(20));
    }

    @Test
    public void testDeleteStore() {
        String storeJson = """
            {
              "name": "Delete Store",
              "quantityProductsInStock": 7
            }
            """;

        Integer storeIdInt =
                given()
                        .contentType(ContentType.JSON)
                        .body(storeJson)
                        .when().post("/store")
                        .then()
                        .statusCode(201)
                        .extract()
                        .path("id");
        Long storeId = storeIdInt.longValue();
        given()
                .when().delete("/store/" + storeId)
                .then()
                .statusCode(204);
        given()
                .when().get("/store/" + storeId)
                .then()
                .statusCode(404);
    }

    @Test
    public void testGetStoreNotFound() {
        given()
                .when().get("/store/999")
                .then()
                .statusCode(404)
                .body(containsString("Store with id of 999 does not exist."));
    }

    @Test
    public void testCreateStoreWithIdError() {
        String storeJsonWithId = """
            {
              "id": 123,
              "name": "Invalid Store",
              "quantityProductsInStock": 10
            }
            """;

        given()
                .contentType(ContentType.JSON)
                .body(storeJsonWithId)
                .when().post("/store")
                .then()
                .statusCode(422)
                .body(containsString("Id was invalidly set on request."));
    }

    @Test
    public void testUpdateStoreNotFound() {
        String updateJson = """
            {
              "name": "Non-existent Store",
              "quantityProductsInStock": 20
            }
            """;

        given()
                .contentType(ContentType.JSON)
                .body(updateJson)
                .when().put("/store/999")
                .then()
                .statusCode(404)
                .body(containsString("Store with id of 999 does not exist."));
    }

    @Test
    public void testPatchStoreSuccess() {
        String storeJson = """
            {
              "name": "Patch Store",
              "quantityProductsInStock": 15
            }
            """;

        Integer storeIdInt =
                given()
                        .contentType(ContentType.JSON)
                        .body(storeJson)
                        .when().post("/store")
                        .then()
                        .statusCode(201)
                        .extract()
                        .path("id");
        Long storeId = storeIdInt.longValue();

        String patchJson = """
            {
              "name": "Patched Store",
              "quantityProductsInStock": 30
            }
            """;

        given()
                .contentType(ContentType.JSON)
                .body(patchJson)
                .when().patch("/store/" + storeId)
                .then()
                .statusCode(200)
                .body("name", is("Patched Store"))
                .body("quantityProductsInStock", is(30));
    }

    @Test
    public void testPatchStoreNotFound() {
        String patchJson = """
            {
              "name": "Non-existent Store",
              "quantityProductsInStock": 20
            }
            """;

        given()
                .contentType(ContentType.JSON)
                .body(patchJson)
                .when().patch("/store/999")
                .then()
                .statusCode(404)
                .body(containsString("Store with id of 999 does not exist."));
    }

    @Test
    public void testDeleteStoreNotFound() {
        given()
                .when().delete("/store/999")
                .then()
                .statusCode(404)
                .body(containsString("Store with id of 999 does not exist."));
    }

    @Test
    public void testStoreEventConstructorAndGetters() {
        Store testStore = new Store("Test Store");
        testStore.quantityProductsInStock = 10;
        
        StoreEvent createEvent = new StoreEvent(testStore, StoreEvent.Operation.CREATE);
        assert createEvent.getStore().equals(testStore);
        assert createEvent.getOperation() == StoreEvent.Operation.CREATE;
        
        StoreEvent updateEvent = new StoreEvent(testStore, StoreEvent.Operation.UPDATE);
        assert updateEvent.getStore().equals(testStore);
        assert updateEvent.getOperation() == StoreEvent.Operation.UPDATE;
        
        StoreEvent deleteEvent = new StoreEvent(testStore, StoreEvent.Operation.DELETE);
        assert deleteEvent.getStore().equals(testStore);
        assert deleteEvent.getOperation() == StoreEvent.Operation.DELETE;
    }
}
