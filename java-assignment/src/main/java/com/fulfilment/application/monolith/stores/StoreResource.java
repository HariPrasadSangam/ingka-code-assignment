package com.fulfilment.application.monolith.stores;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.List;

@Path("store")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class StoreResource {

  @Inject Event<StoreEvent> storeEvent;

  private static final Logger LOGGER = Logger.getLogger(StoreResource.class.getName());

  @GET
  public List<Store> get() {
    return Store.listAll(Sort.by("name"));
  }

  @GET
  @Path("{id}")
  public Store getSingle(Long id) {
    Store entity = Store.findById(id);
    if (entity == null) {
      throw new WebApplicationException("Store with id of " + id + " does not exist.", 404);
    }
    return entity;
  }

  @POST
  @Transactional
  public Response create(Store store) {
    LOGGER.debugf("Creating store: %s", store.name);
    if (store.id != null) {
      throw new WebApplicationException("Id was invalidly set on request.", 422);
    }

    store.persist();
    LOGGER.debugf("Create store, firing event to call legacy system: %s", store.name);
    storeEvent.fire(new StoreEvent(store, StoreEvent.Operation.CREATE));

    return Response.ok(store).status(201).build();
  }

  @PUT
  @Path("{id}")
  @Transactional
  public Store update(Long id, Store updatedStore) {
    LOGGER.debugf("Updating store: %s", updatedStore.name);
    if (updatedStore.name == null) {
      throw new WebApplicationException("Store Name was not set on request.", 422);
    }

    Store entity = Store.findById(id);

    if (entity == null) {
      throw new WebApplicationException("Store with id of " + id + " does not exist.", 404);
    }

    entity.name = updatedStore.name;
    entity.quantityProductsInStock = updatedStore.quantityProductsInStock;

    LOGGER.debugf("Update store, firing event to call legacy system: %s", updatedStore.name);
    storeEvent.fire(new StoreEvent(entity, StoreEvent.Operation.UPDATE));

    return entity;
  }

  @PATCH
  @Path("{id}")
  @Transactional
  public Store patch(Long id, Store updatedStore) {
    LOGGER.debugf("Patch store: %s", updatedStore.name);
    if (updatedStore.name == null) {
      throw new WebApplicationException("Store Name was not set on request.", 422);
    }

    Store entity = Store.findById(id);

    if (entity == null) {
      throw new WebApplicationException("Store with id of " + id + " does not exist.", 404);
    }

    if (updatedStore.name != null) {
      entity.name = updatedStore.name;
    }

    if (updatedStore.quantityProductsInStock != 0) {
      entity.quantityProductsInStock = updatedStore.quantityProductsInStock;
    }
    LOGGER.debugf("Patch store, firing event to call legacy system: %s", entity.name);
    storeEvent.fire(new StoreEvent(entity, StoreEvent.Operation.UPDATE));

    return entity;
  }

  @DELETE
  @Path("{id}")
  @Transactional
  public Response delete(Long id) {
    LOGGER.debugf("Deleting store with Id: %s", id);
    Store entity = Store.findById(id);
    if (entity == null) {
      throw new WebApplicationException("Store with id of " + id + " does not exist.", 404);
    }
    LOGGER.debugf("Delete store, firing event to call legacy system");
    storeEvent.fire(new StoreEvent(entity, StoreEvent.Operation.DELETE));
    entity.delete();
    return Response.status(204).build();
  }
}
