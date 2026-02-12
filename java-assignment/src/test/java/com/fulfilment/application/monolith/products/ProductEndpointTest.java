package com.fulfilment.application.monolith.products;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.IsNot.not;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class ProductEndpointTest {

  @Test
  public void testCrudProduct() {
    final String path = "product";

    // List all, should have all 3 products the database has initially:
    given()
        .when()
        .get(path)
        .then()
        .statusCode(200)
        .body(containsString("TONSTAD"), containsString("KALLAX"), containsString("BESTÅ"));

    // Delete the TONSTAD:
    given().when().delete(path + "/1").then().statusCode(204);

    // List all, TONSTAD should be missing now:
    given()
        .when()
        .get(path)
        .then()
        .statusCode(200)
        .body(not(containsString("TONSTAD")), containsString("KALLAX"), containsString("BESTÅ"));
  }

  @Test
  public void testGetSingleProductSuccess() {
    given()
        .when()
        .get("/product/2")
        .then()
        .statusCode(200)
        .body("name", containsString("KALLAX"));
  }

  @Test
  public void testGetSingleProductNotFound() {
    given()
        .when()
        .get("/product/999")
        .then()
        .statusCode(404)
        .body(containsString("Product with id of 999 does not exist."));
  }

  @Test
  public void testCreateProductSuccess() {
    Product newProduct = new Product();
    newProduct.name = "NEW_PRODUCT";
    newProduct.description = "Test product description";
    newProduct.price = new BigDecimal("99.99");
    newProduct.stock = 50;

    given()
        .contentType(ContentType.JSON)
        .body(newProduct)
        .when()
        .post("/product")
        .then()
        .statusCode(201)
        .body("name", equalTo("NEW_PRODUCT"))
        .body("description", equalTo("Test product description"))
        .body("price", equalTo(99.99f))
        .body("stock", equalTo(50));
  }

  @Test
  public void testCreateProductWithIdError() {
    Product productWithId = new Product();
    productWithId.id = 123L;
    productWithId.name = "INVALID_PRODUCT";

    given()
        .contentType(ContentType.JSON)
        .body(productWithId)
        .when()
        .post("/product")
        .then()
        .statusCode(422)
        .body(containsString("Id was invalidly set on request."));
  }

  @Test
  public void testUpdateProductSuccess() {
    Product updateProduct = new Product();
    updateProduct.name = "UPDATED_KALLAX";
    updateProduct.description = "Updated description";
    updateProduct.price = new BigDecimal("149.99");
    updateProduct.stock = 25;

    given()
        .contentType(ContentType.JSON)
        .body(updateProduct)
        .when()
        .put("/product/2")
        .then()
        .statusCode(200)
        .body("name", equalTo("UPDATED_KALLAX"))
        .body("description", equalTo("Updated description"))
        .body("price", equalTo(149.99f))
        .body("stock", equalTo(25));
  }

  @Test
  public void testUpdateProductMissingNameError() {
    Product productWithoutName = new Product();
    productWithoutName.description = "Description without name";
    productWithoutName.price = new BigDecimal("99.99");
    productWithoutName.stock = 10;

    given()
        .contentType(ContentType.JSON)
        .body(productWithoutName)
        .when()
        .put("/product/2")
        .then()
        .statusCode(422)
        .body(containsString("Product Name was not set on request."));
  }

  @Test
  public void testUpdateProductNotFound() {
    Product updateProduct = new Product();
    updateProduct.name = "NON_EXISTENT";

    given()
        .contentType(ContentType.JSON)
        .body(updateProduct)
        .when()
        .put("/product/999")
        .then()
        .statusCode(404)
        .body(containsString("Product with id of 999 does not exist."));
  }

  @Test
  public void testDeleteProductNotFound() {
    given()
        .when()
        .delete("/product/999")
        .then()
        .statusCode(404)
        .body(containsString("Product with id of 999 does not exist."));
  }

  @Test
  public void testProductConstructorWithName() {
    Product product = new Product("TEST_NAME");
    assert product.name.equals("TEST_NAME");
    assert product.description == null;
    assert product.price == null;
    assert product.stock == 0;
  }
}
