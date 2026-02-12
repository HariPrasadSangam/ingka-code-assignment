package com.fulfilment.application.monolith.warehousefullfilment.database;

import com.fulfilment.application.monolith.warehousefullfilment.adapters.database.WarehouseFullfilmentRepository;
import com.fulfilment.application.monolith.warehousefullfilment.domain.model.WarehouseFullfilment;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class WarehouseFullfilmentRepositoryTest {

    @Inject
    WarehouseFullfilmentRepository repository;

    @BeforeEach
    @Transactional
    void cleanDb() {
        repository.deleteAll();
    }

    @Test
    @Transactional
    void create_should_persist_entity() {
        WarehouseFullfilment wf =
                new WarehouseFullfilment("MWH-001", "STORE-1",
                        "PRODUCT-1", ZonedDateTime.now());

        repository.create(wf);

        long count = repository.count();
        assertEquals(1, count);
    }

    @Test
    @Transactional
    void findNumberofWarehousesForAProductPerStore_should_return_correct_count() {
        WarehouseFullfilment wf1 =
                new WarehouseFullfilment("MWH-001", "STORE-1", "PRODUCT-1",ZonedDateTime.now());
        WarehouseFullfilment wf2 =
                new WarehouseFullfilment("MWH-002", "STORE-1", "PRODUCT-1", ZonedDateTime.now());

        repository.create(wf1);
        repository.create(wf2);

        long result =
                repository.findNumberofWarehousesForAProductPerStore(wf1);

        assertEquals(2, result);
    }

    @Test
    @Transactional
    void findNumberofWarehousesPerStore_should_return_distinct_warehouse_count() {
        WarehouseFullfilment wf1 =
                new WarehouseFullfilment("MWH-001", "STORE-1", "PRODUCT-1",ZonedDateTime.now());
        WarehouseFullfilment wf2 =
                new WarehouseFullfilment("MWH-002", "STORE-1", "PRODUCT-2", ZonedDateTime.now());
        WarehouseFullfilment wf3 =
                new WarehouseFullfilment("MWH-001", "STORE-1", "PRODUCT-3", ZonedDateTime.now());

        repository.create(wf1);
        repository.create(wf2);
        repository.create(wf3);

        long result =
                repository.findNumberofWarehousesPerStore(wf1);

        assertEquals(2, result);
    }

    @Test
    @Transactional
    void findNumberofWarehousesPerproduct_should_return_distinct_product_count() {
        WarehouseFullfilment wf1 =
                new WarehouseFullfilment("MWH-001", "STORE-1", "PRODUCT-1", ZonedDateTime.now());
        WarehouseFullfilment wf2 =
                new WarehouseFullfilment("MWH-001", "STORE-2", "PRODUCT-2", ZonedDateTime.now());
        WarehouseFullfilment wf3 =
                new WarehouseFullfilment("MWH-001", "STORE-3", "PRODUCT-2", ZonedDateTime.now());

        repository.create(wf1);
        repository.create(wf2);
        repository.create(wf3);

        long result =
                repository.findNumberofWarehousesPerproduct(wf1);

        assertEquals(2, result);
    }
}

