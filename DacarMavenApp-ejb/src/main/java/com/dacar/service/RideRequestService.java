/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dacar.service;

import com.dacar.entity.RideRequest;
import com.dacar.facade.RideRequestFacade;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

/**
 *
 * @author Jon Wetherbee <jon.wetherbee@gmail.com>
 */
@Singleton
public class RideRequestService {

  @EJB
  private RideRequestFacade reqFacade;

  @EJB
  private NewReqService newReqService;

  @EJB
  private UpdatedReqService updatedReqService;

  @EJB
  private CancelledReqService cancelledReqService;

  private boolean enabled = true;

  public RideRequestService() {

  }

  /**
   * @return the enabled
   */
  public boolean isEnabled() {
    return enabled;
  }

  /**
   * @param enabled the enabled to set
   */
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  @Schedule(dayOfWeek = "*", month = "*", hour = "*", dayOfMonth = "*", year = "*", minute = "*", second = "0,10,20,30,40,50")
  public void myTimer() {

    if (!enabled) {
      return;
    }

    try {
      enabled = false;
      System.out.println("Timer event: " + new Date());

      assignCancelledReqs();
      assignUpdatedReqs();
      assignNewReqs();
    } finally {
      enabled = true;
    }
  }

  private void assignCancelledReqs() {

    List<RideRequest> reqs = reqFacade.getCancelledRequests();
    for (RideRequest req : reqs) {
      cancelledReqService.handleRequest(req);
    }

  }

  private void assignNewReqs() {

    List<RideRequest> reqs = reqFacade.getNewRequests();
    for (RideRequest req : reqs) {
      newReqService.handleRequest(req);
    }

  }

  private void assignUpdatedReqs() {

    List<RideRequest> reqs = reqFacade.getUpdatedRequests();
    for (RideRequest req : reqs) {
      updatedReqService.handleRequest(req);
    }

  }
}
