/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dacar.facade;

import com.dacar.entity.GeoTempData;
import com.dacar.entity.QueryNames;
import com.dacar.entity.RideRequest;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jon Wetherbee <jon.wetherbee@gmail.com>
 */
@Stateless
public class GeoTempDataFacade extends AbstractFacade<GeoTempData> {

  @PersistenceContext(unitName = "DacarWebModulePU")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public GeoTempDataFacade() {
    super(GeoTempData.class);
  }

  public List<String> getReqKeys() {
    return em.createNamedQuery(QueryNames.GeoTempData_findReqKeys, String.class).getResultList();
  }

  public List<GeoTempData> getByReqKey(String reqKey) {
    return em.createNamedQuery(QueryNames.GeoTempData_findByReqKey, GeoTempData.class).setParameter("reqKey", reqKey).getResultList();
  }

  public List<String> getReqKeysByXYT(int x, int y, int t_lower, int t_upper) {
    return em.createNamedQuery(QueryNames.GeoTempData_findByXYTlTu, String.class).setParameter("x", x).setParameter("y", y).setParameter("t_lower", t_lower).setParameter("t_upper", t_upper).getResultList();
  }

  public List<String> getReqKeysByX(int x) {
    return em.createNamedQuery(QueryNames.GeoTempData_findByX, String.class).setParameter("x", x).getResultList();
  }

  public List<String> getReqKeysByY(int y) {
    return em.createNamedQuery(QueryNames.GeoTempData_findByY, String.class).setParameter("y", y).getResultList();
  }

  public List<String> getReqKeysByT(int t) {
    return em.createNamedQuery(QueryNames.GeoTempData_findByT, String.class).setParameter("t", t).getResultList();
  }

}
