package com.fulfilment.application.monolith.stores;

import com.fulfilment.application.monolith.location.LocationGateway;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@QuarkusTest
public class LegacyStoreManagerGatewayTest {

    private LegacyStoreManagerGateway legacyStoreManagerGateway;

    @BeforeEach
    void setUp() {
        legacyStoreManagerGateway = new LegacyStoreManagerGateway();
    }

    @Test
    void testCreateStoreOnLegacySystem() {
        Store store = new Store();
        store.name = "StoreLegacy";
        store.quantityProductsInStock = 10;
        assertDoesNotThrow(() ->
                legacyStoreManagerGateway.createStoreOnLegacySystem(store)
        );
    }

    @Test
    void testUpdateStoreOnLegacySystem() {
        Store store = new Store();
        store.name = "StoreLegacy";
        store.quantityProductsInStock = 20;
        assertDoesNotThrow(() ->
                legacyStoreManagerGateway.updateStoreOnLegacySystem(store)
        );
    }
}
