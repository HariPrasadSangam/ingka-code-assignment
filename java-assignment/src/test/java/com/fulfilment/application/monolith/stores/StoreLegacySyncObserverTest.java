package com.fulfilment.application.monolith.stores;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.verify;

@QuarkusTest
public class StoreLegacySyncObserverTest {

    @Inject
    StoreLegacySyncObserver storeLegacySyncObserver;

    @InjectMock
    LegacyStoreManagerGateway legacyStoreManagerGateway;

    @Test
    void testAfterCommit_FullChain() {
        Store store = new Store();
        StoreEvent event =
                new StoreEvent(
                        store,
                        StoreEvent.Operation.CREATE
                );

        storeLegacySyncObserver.afterStoreCommitted(event);

        verify(legacyStoreManagerGateway)
                .createStoreOnLegacySystem(store);
    }
}


