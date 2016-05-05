/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dacar.service;

import com.dacar.entity.RideRequest;
import com.dacar.facade.RideRequestFacade;
import javax.ejb.EJB;

/**
 *
 * @author Jon Wetherbee <jon.wetherbee@gmail.com>
 */
public abstract class RequestService {

  Class reqClass;

  @EJB
  RideRequestFacade facade;

  protected RequestService(Class reqClass) {
    this.reqClass = reqClass;
  }

  protected abstract void handleRequest(RideRequest req);

}
