/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dacar.facade;

import com.dacar.entity.Rider;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jon Wetherbee <jon.wetherbee@gmail.com>
 */
@Stateless
public class RiderFacade extends AbstractFacade<Rider> {

  @PersistenceContext(unitName = "DacarWebModulePU")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public RiderFacade() {
    super(Rider.class);
  }

}
