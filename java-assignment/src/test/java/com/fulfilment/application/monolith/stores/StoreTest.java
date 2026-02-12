package com.fulfilment.application.monolith.stores;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class StoreTest {
    @Test
    void testConstructorSetsName() {
        String storeName = "New";
        Store store = new Store(storeName);
        assertEquals(storeName, store.name);
    }
}
