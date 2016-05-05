/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dacar.service;

import com.dacar.entity.RideRequest;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;

/**
 *
 * @author Jon Wetherbee <jon.wetherbee@gmail.com>
 */
@Singleton
@LocalBean
public class CancelledReqService extends RequestService {

  public CancelledReqService() {
    super(CancelledReqService.class);
  }

  @Override
  public void handleRequest(RideRequest req) {
    System.out.println("Handling Cancelled request");
  }

}
