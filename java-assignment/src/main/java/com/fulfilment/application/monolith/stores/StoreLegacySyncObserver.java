package com.fulfilment.application.monolith.stores;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.TransactionPhase;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

/**
 * Observes store change events after the transaction has committed, so that the legacy
 * system receives only confirmed data.
 */
@ApplicationScoped
public class StoreLegacySyncObserver {

  private static final Logger LOGGER = Logger.getLogger(StoreLegacySyncObserver.class);

  @Inject LegacyStoreManagerGateway legacyStoreManagerGateway;

  public void afterStoreCommitted(
      @Observes(during = TransactionPhase.AFTER_SUCCESS) StoreEvent event) {
    Store store = event.getStore();
    if (store == null) {
      return;
    }
    try {
      switch (event.getOperation()) {
        case CREATE:
          legacyStoreManagerGateway.createStoreOnLegacySystem(store);
          LOGGER.debugf("Notify Legacy system about store creation: %s", store.name);
          break;
        case UPDATE:
          legacyStoreManagerGateway.updateStoreOnLegacySystem(store);
          LOGGER.debugf("Notify Legacy system about store updation: %s", store.name);
          break;
        case DELETE:
          legacyStoreManagerGateway.updateStoreOnLegacySystem(store);
          LOGGER.debugf("Notify Legacy system about store deletion: %s", store.name);
          break;
        default:
          LOGGER.warnf("Unknown store operation: %s", event.getOperation());
      }
    } catch (Exception e) {
      LOGGER.errorf(e, "Failed to sync store to legacy system: %s", store.name);
      throw e;
    }
  }
}
