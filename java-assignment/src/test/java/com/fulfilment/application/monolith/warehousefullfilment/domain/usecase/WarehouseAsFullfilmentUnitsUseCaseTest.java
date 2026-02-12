package com.fulfilment.application.monolith.warehousefullfilment.domain.usecase;

import com.fulfilment.application.monolith.warehousefullfilment.domain.model.WarehouseFullfilment;
import com.fulfilment.application.monolith.warehousefullfilment.domain.port.WarehouseFullfilmentStore;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@QuarkusTest
public class WarehouseAsFullfilmentUnitsUseCaseTest {
    @InjectMock
    WarehouseFullfilmentStore warehouseFullfilmentStore;

    @Inject
    WarehouseAsFullfilmentUnitsUseCase warehouseAsFullfilmentUnitsUseCase;

    private WarehouseFullfilment testWarehouseFullfilment;

    @BeforeEach
    void setUp() {
        testWarehouseFullfilment = new WarehouseFullfilment("MWH-001", "STORE-1", "PRODUCT-1", ZonedDateTime.now());
    }

    @Test
    @DisplayName("Should fail when warehouses per product per store exceeds limit (2)")
    void shouldFailWhenWarehousePerProductPerStoreExceeds() {
        when(warehouseFullfilmentStore.findNumberofWarehousesForAProductPerStore(testWarehouseFullfilment))
                .thenReturn(3L);

        IllegalStateException exception = assertThrows(IllegalStateException.class, 
                () -> warehouseAsFullfilmentUnitsUseCase.createWarehouseFullfilment(testWarehouseFullfilment));
        
        assertEquals("A product can be fulfilled by max 2 warehouses per store.", exception.getMessage());
        verify(warehouseFullfilmentStore, never()).create(any());
    }

    @Test
    @DisplayName("Should successfully create warehouse fulfillment when all limits are respected")
    void shouldCreateWarehouseFullfilmentWhenAllLimitsAreRespected() {
        when(warehouseFullfilmentStore.findNumberofWarehousesForAProductPerStore(testWarehouseFullfilment))
                .thenReturn(1L);
        when(warehouseFullfilmentStore.findNumberofWarehousesPerStore(testWarehouseFullfilment))
                .thenReturn(2L);
        when(warehouseFullfilmentStore.findNumberofWarehousesPerproduct(testWarehouseFullfilment))
                .thenReturn(3L);

        warehouseAsFullfilmentUnitsUseCase.createWarehouseFullfilment(testWarehouseFullfilment);

        verify(warehouseFullfilmentStore, times(1)).create(testWarehouseFullfilment);
    }

    @Test
    @DisplayName("Should fail when warehouses per store exceeds limit (3)")
    void shouldFailWhenWarehousesPerStoreExceeds() {
        when(warehouseFullfilmentStore.findNumberofWarehousesForAProductPerStore(testWarehouseFullfilment))
                .thenReturn(1L);
        when(warehouseFullfilmentStore.findNumberofWarehousesPerStore(testWarehouseFullfilment))
                .thenReturn(4L);

        IllegalStateException exception = assertThrows(IllegalStateException.class, 
                () -> warehouseAsFullfilmentUnitsUseCase.createWarehouseFullfilment(testWarehouseFullfilment));
        
        assertEquals("A store can be fulfilled by max 3 warehouses.", exception.getMessage());
        verify(warehouseFullfilmentStore, never()).create(any());
    }

    @Test
    @DisplayName("Should fail when products per warehouse exceeds limit (5)")
    void shouldFailWhenProductsPerWarehouseExceeds() {
        when(warehouseFullfilmentStore.findNumberofWarehousesForAProductPerStore(testWarehouseFullfilment))
                .thenReturn(1L);
        when(warehouseFullfilmentStore.findNumberofWarehousesPerStore(testWarehouseFullfilment))
                .thenReturn(2L);
        when(warehouseFullfilmentStore.findNumberofWarehousesPerproduct(testWarehouseFullfilment))
                .thenReturn(6L);

        IllegalStateException exception = assertThrows(IllegalStateException.class, 
                () -> warehouseAsFullfilmentUnitsUseCase.createWarehouseFullfilment(testWarehouseFullfilment));
        
        assertEquals("A warehouse can fulfil max 5 product types.", exception.getMessage());
        verify(warehouseFullfilmentStore, never()).create(any());
    }

    @Test
    @DisplayName("Should successfully create when exactly at limits")
    void shouldCreateWhenExactlyAtLimits() {
        when(warehouseFullfilmentStore.findNumberofWarehousesForAProductPerStore(testWarehouseFullfilment))
                .thenReturn(2L);
        when(warehouseFullfilmentStore.findNumberofWarehousesPerStore(testWarehouseFullfilment))
                .thenReturn(3L);
        when(warehouseFullfilmentStore.findNumberofWarehousesPerproduct(testWarehouseFullfilment))
                .thenReturn(5L);

        warehouseAsFullfilmentUnitsUseCase.createWarehouseFullfilment(testWarehouseFullfilment);

        verify(warehouseFullfilmentStore, times(1)).create(testWarehouseFullfilment);
    }

    @Test
    @DisplayName("Should handle zero counts gracefully")
    void shouldHandleZeroCountsGracefully() {
        when(warehouseFullfilmentStore.findNumberofWarehousesForAProductPerStore(testWarehouseFullfilment))
                .thenReturn(0L);
        when(warehouseFullfilmentStore.findNumberofWarehousesPerStore(testWarehouseFullfilment))
                .thenReturn(0L);
        when(warehouseFullfilmentStore.findNumberofWarehousesPerproduct(testWarehouseFullfilment))
                .thenReturn(0L);

        warehouseAsFullfilmentUnitsUseCase.createWarehouseFullfilment(testWarehouseFullfilment);

        verify(warehouseFullfilmentStore, times(1)).create(testWarehouseFullfilment);
    }

    @Test
    @DisplayName("Should fail when first validation fails (warehouses per product per store)")
    void shouldFailOnFirstValidation() {
        when(warehouseFullfilmentStore.findNumberofWarehousesForAProductPerStore(testWarehouseFullfilment))
                .thenReturn(5L);

        IllegalStateException exception = assertThrows(IllegalStateException.class, 
                () -> warehouseAsFullfilmentUnitsUseCase.createWarehouseFullfilment(testWarehouseFullfilment));
        
        assertEquals("A product can be fulfilled by max 2 warehouses per store.", exception.getMessage());
        verify(warehouseFullfilmentStore, never()).create(any());
        verify(warehouseFullfilmentStore, times(1)).findNumberofWarehousesForAProductPerStore(testWarehouseFullfilment);
        verify(warehouseFullfilmentStore, never()).findNumberofWarehousesPerStore(any());
        verify(warehouseFullfilmentStore, never()).findNumberofWarehousesPerproduct(any());
    }

}

