package com.fulfilment.application.monolith.stores;

/**
 * Event fired when a Store is created, updated or deleted. Observed after transaction commit
 * so that the legacy system is notified only after data is persisted.
 */
public class StoreEvent {

  public enum Operation {
    CREATE,
    UPDATE,
    DELETE
  }

  private final Store store;
  private final Operation operation;

  public StoreEvent(Store store, Operation operation) {
    this.store = store;
    this.operation = operation;
  }

  public Store getStore() {
    return store;
  }

  public Operation getOperation() {
    return operation;
  }
}
