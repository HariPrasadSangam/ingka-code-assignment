package com.fulfilment.application.monolith.location;

import com.fulfilment.application.monolith.warehouses.domain.models.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationGatewayTest {

  private LocationGateway locationGateway;

  @BeforeEach
  void setUp() {
    locationGateway = new LocationGateway();
  }

  @Test
  void testWhenResolveExistingLocationShouldReturnLocation() {
    Location location = locationGateway.resolveByIdentifier("ZWOLLE-001");
    assertNotNull(location);
    assertEquals("ZWOLLE-001", location.identification);
    assertEquals(1, location.maxNumberOfWarehouses);
    assertEquals(40, location.maxCapacity);
  }

  @Test
  public void testWhenResolveNonExistingLocationShouldReturnNull() {
    Location location = locationGateway.resolveByIdentifier("ZWOLLE-005");
    assertNull(location);
  }

  @Test
  public void testWhenIdentifierEmpty() {
    RuntimeException ex = assertThrows(RuntimeException.class,
            () -> locationGateway.resolveByIdentifier(""));
    assertTrue(ex.getMessage().contains("Identifier cannot be empty"));
  }
}
