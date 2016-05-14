/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dacar.facade;

import com.dacar.entity.QueryNames;
import com.dacar.entity.RideRequest;
import com.dacar.entity.RouteCompatibility;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jonwetherbee
 */
@Stateless
public class RouteCompatibilityFacade extends AbstractFacade<RouteCompatibility> {

  @PersistenceContext(unitName = "DacarWebModulePU")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public RouteCompatibilityFacade() {
    super(RouteCompatibility.class);
  }
  
  public List<RouteCompatibility> findByOrigin(String origin) {
    return em.createNamedQuery(QueryNames.RouteCompatibility_findByOrigin, RouteCompatibility.class).
        setParameter("origin", origin).
        getResultList();
  }
  
  public List<RouteCompatibility> findByWrapperEndpoints(String origin, String destination) {
    return em.createNamedQuery(QueryNames.RouteCompatibility_findByWrapperEndpoints, RouteCompatibility.class).
        setParameter("origin", origin).
        setParameter("destination", destination).
        getResultList();
  }
  
  public List<RouteCompatibility> findByAllEndpoints(String origin, String destination, String otherOrigin, String otherDestination) {
    return em.createNamedQuery(QueryNames.RouteCompatibility_findByAllEndpoints, RouteCompatibility.class).
        setParameter("origin", origin).
        setParameter("destination", destination).
        setParameter("otherOrigin", otherOrigin).
        setParameter("otherDestination", otherDestination).
        getResultList();
  }
}
