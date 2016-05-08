/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dacar.facade;

import com.dacar.entity.QueryNames;
import com.dacar.entity.RideRequest;
import com.dacar.entity.RideRequest.RequestStatusType;
import java.util.List;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jon Wetherbee <jon.wetherbee@gmail.com>
 */
@Stateless
public class RideRequestFacade extends AbstractFacade<RideRequest> {

  @PersistenceContext(unitName = "DacarWebModulePU")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public RideRequestFacade() {
    super(RideRequest.class);
  }

  public RideRequest newRideRequest(RideRequest entity) {
    entity.setReqKey(UUID.randomUUID().toString());
    entity.setStatus(RideRequest.RequestStatusType.NEW);
    super.create(entity);
    return entity;
  }

  public RideRequest updateRideRequest(RideRequest entity) {
    RideRequest existing = super.find(entity.getReqKey());
    if (existing != null) {
      if (existing.getStatus() == RequestStatusType.CANCELLED) {
        throw new RuntimeException("Ride request has already been cancelled");
      } else {
        if (entity.getRide() == null) {
          if (entity.getStatus() == RequestStatusType.UPDATED) {
            entity.setStatus(RequestStatusType.NEW);
          }
        } else {
          entity.setStatus(RequestStatusType.UPDATED);
        }
        entity.setRoutes(null);
        return super.edit(entity);
      }
    }
    return null;
  }

  public void cancelRideRequest(String reqKey) {
    if (reqKey == null) {
      throw new RuntimeException("Missing Ride Code during Cancel");
    }
    RideRequest entity = new RideRequest();
    entity.setReqKey(reqKey);
    entity.setStatus(RideRequest.RequestStatusType.CANCELLED);
    super.edit(entity);
  }

  public List<RideRequest> getNewRequests() {
    return em.createNamedQuery(QueryNames.RideRequest_findByStatus).
            setParameter("status", RequestStatusType.NEW).
            getResultList();
  }

  public List<RideRequest> getUpdatedRequests() {
    return em.createNamedQuery(QueryNames.RideRequest_findByStatus).
            setParameter("status", RequestStatusType.UPDATED).
            getResultList();
  }

  public List<RideRequest> getCancelledRequests() {
    return em.createNamedQuery(QueryNames.RideRequest_findByStatus).
            setParameter("status", RequestStatusType.CANCELLED).
            getResultList();
  }

  public void removeByReqKeyPattern(String reqKeyPattern) {
    if (reqKeyPattern == null) {
      throw new RuntimeException("Missing Ride Code during Cancel");
    }

    em.createQuery("delete from RideRequest o where o.reqKey like :reqKeyPattern");
  }

}
