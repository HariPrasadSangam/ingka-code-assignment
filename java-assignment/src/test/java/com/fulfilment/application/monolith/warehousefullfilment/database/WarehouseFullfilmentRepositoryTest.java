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
    void testCreateWarehouseFullfilment() {
        WarehouseFullfilment wf =
                new WarehouseFullfilment(1L, 1L, 1L, ZonedDateTime.now());

        repository.create(wf);

        long count = repository.count();
        assertEquals(1, count);
    }

    @Test
    @Transactional
    void testNumberOfWarehousesForAProductPerStore() {
        WarehouseFullfilment wf1 =
                new WarehouseFullfilment(1L, 1L, 1L, ZonedDateTime.now());
        WarehouseFullfilment wf2 =
                new WarehouseFullfilment(1L, 1L, 2L, ZonedDateTime.now());

        repository.create(wf1);
        repository.create(wf2);

        long result =
                repository.findNumberofWarehousesForAProductPerStore(wf1);

        assertEquals(2, result);
    }

    @Test
    @Transactional
    void testNumberofWarehousesPerStore() {
        WarehouseFullfilment wf1 =
                new WarehouseFullfilment(1L, 1L, 1L, ZonedDateTime.now());
        WarehouseFullfilment wf2 =
                new WarehouseFullfilment(1L, 2L, 2L, ZonedDateTime.now());
        WarehouseFullfilment wf3 =
                new WarehouseFullfilment(1L, 3L, 1L, ZonedDateTime.now());

        repository.create(wf1);
        repository.create(wf2);
        repository.create(wf3);

        long result =
                repository.findNumberofWarehousesPerStore(wf1);

        assertEquals(2, result);
    }

    @Test
    @Transactional
    void testNumberofWarehousesPerProduct() {
        WarehouseFullfilment wf1 =
                new WarehouseFullfilment(1L, 1L, 1L, ZonedDateTime.now());
        WarehouseFullfilment wf2 =
                new WarehouseFullfilment(2L, 2L, 1L, ZonedDateTime.now());
        WarehouseFullfilment wf3 =
                new WarehouseFullfilment(3L, 2L, 1L, ZonedDateTime.now());

        repository.create(wf1);
        repository.create(wf2);
        repository.create(wf3);

        long result =
                repository.findNumberofWarehousesPerproduct(wf1);

        assertEquals(2, result);
    }
}

