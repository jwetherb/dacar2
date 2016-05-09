/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dacar.service;

import com.dacar.entity.RideRequest;
import static com.dacar.entity.RideRequest.RiderType.*;
import com.dacar.facade.RideRequestFacade;
import com.dacar.facade.UnmatchedPoolFacade;
import java.util.HashSet;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;

/**
 *
 * @author Jon Wetherbee <jon.wetherbee@gmail.com>
 */
@Singleton
@LocalBean
public class NewReqService extends RequestService {

  @EJB
  private MapDataService mapService;

  @EJB
  private RideRequestFacade reqFacade;

  @EJB
  private UnmatchedPoolFacade unmatched;

  public NewReqService() {
    super(NewReqService.class);
  }

  /**
   * Process a new RideRequest. (N0)
   *
   * 1) Derive the geo-temporal coordinates in the ride, across all available routes, and store in an in-memory map. 
   * If able to be a passenger, try to match with an existing ride. If must be a driver create a new trip.
   *
   * 1) Switch on the RiderType
   *
   * @param req the new RideRequest to process
   */
  @Override
  public void handleRequest(RideRequest req) {
    System.out.println("Handling New request");
    
//    //  Process this request's GR, in case it is the first time the system has
//    //  seen this GR
//    mapService.processGR(req);
//
//    Set<RideRequest> matchedReqs = new HashSet<RideRequest>();
//    
//    //  Look for all reasonable matches among req's in the Unmatched pool
//    Set<String> otherReqKeys = mapService.getGTDMatches(req);
//    final RideRequest.RiderType riderType = req.getRiderType();
//
//    assignDriver(riderType, req, matchedReqs);
//
//    for (String otherReqKey : otherReqKeys) {
//      RideRequest other = reqFacade.find(otherReqKey);
//    }

  }

  private void assignDriver(final RideRequest.RiderType riderType, RideRequest req, Set<RideRequest> matchedReqs) {
    RideRequest currDriver = null;
    if (riderType == MUST_BE_DRIVER || riderType == PREFER_TO_BE_DRIVER || riderType == CAN_BE_DRIVER_OR_PASSENGER) {
      currDriver = req;
      matchedReqs.add(req);
    }
  }

}
