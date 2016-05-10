/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dacar.facade;

import com.dacar.entity.GeoTempRoutes;
import com.dacar.entity.QueryNames;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jonwetherbee
 */
@Stateless
public class GeoTempRoutesFacade extends AbstractFacade<GeoTempRoutes> {

  @PersistenceContext(unitName = "DacarWebModulePU")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public GeoTempRoutesFacade() {
    super(GeoTempRoutes.class);
  }

  /**
   * <code>
   * @NamedQuery(name = QueryNames.GeoTempRoutes_findByEndPoints,
   *     query = "select o from GeoTempRoutes o where" +
   *             "o.startGC_lat = :startGC_lat " +
   *             "and o.startGC_lon = :startGC_lon" +
   *             "and o.endGC_lat = :endGC_lat" +
   *             "and o.endGC_lon = :endGC+lon")
   * </code>
   * @param y
   * @return
   */
  public GeoTempRoutes getGTRByEndPoints(int startGC_lat, int startGC_lon, int endGC_lat, int endGC_lon) {
    List<GeoTempRoutes> gtrs = em.createNamedQuery(QueryNames.GeoTempRoutes_findByEndPoints, GeoTempRoutes.class).
        setParameter("startGC_lat", startGC_lat).
        setParameter("startGC_lon", startGC_lon).
        setParameter("endGC_lat", endGC_lat).
        setParameter("endGC_lon", endGC_lon).
        getResultList();

    if (gtrs.size() == 0) {
      return null;
    } else if (gtrs.size() == 1) {
      return gtrs.get(0);
    }
    throw new RuntimeException("DAC-600: Too many results found by getGTRByEndPoints()");
  }

}
