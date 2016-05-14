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
import com.dacar.maps.dacarmaps.DacarMapService;
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
  private DacarMapService mapService;

  @EJB
  private RideRequestFacade reqFacade;

  @EJB
  private UnmatchedPoolFacade unmatched;

  public NewReqService() {
    super(NewReqService.class);
  }

  /**
   * When NEW reqs are processed, they look for matches only among the UNMATCHED reqs (at first): Calc compatibility
   * with each of the UNMATCHED reqs (narrow choices by region, proximity, and maybe time) Many of these calc’s can be
   * retrieved from the cache; we only ever calc compatibility once between route A and route B and persist the result
   * as a COMPATIBILITY object or something Store results of compat check in a Map for each req key=reqKey, value=compat
   * object Compat results stored as a single JSON column
   *
   *
   * Process a new RideRequest. (N0)
   *
   * 1) Derive the geo-temporal coordinates in the ride, across all available routes, and store in an in-memory map. If
   * able to be a passenger, try to match with an existing ride. If must be a driver create a new trip.
   *
   * 1) Switch on the RiderType
   *
   * @param req the new RideRequest to process
   */
  @Override
  public void handleRequest(RideRequest req) {
    System.out.println("Handling New request");

    //  Get the Routes for this request (key is endpoints: "[StartLocation]->[EndLocation]")
    processEndpoints(req);

    //  Generate the compatibility map for this request (key is endpoints: "[StartLocation]->[EndLocation]")
    //  with each of the UNMATCHED reqs
    for (RideRequest uReq: reqFacade.getRequestsByStatus(RideRequest.RequestStatusType.UNMATCHED)) {
      mapService.calcCompatibility(req, uReq);
      mapService.calcCompatibility(uReq, req);
    }
    
    //  Do other stuff to try to find a match
    
    //  If no match is found, set status to UNMATCHED and save
    req.setStatus(RideRequest.RequestStatusType.UNMATCHED);
    reqFacade.edit(req);
  }

  private void assignDriver(final RideRequest.RiderType riderType, RideRequest req, Set<RideRequest> matchedReqs) {
    RideRequest currDriver = null;
    if (riderType == MUST_BE_DRIVER || riderType == PREFER_TO_BE_DRIVER || riderType == CAN_BE_DRIVER_OR_PASSENGER) {
      currDriver = req;
      matchedReqs.add(req);
    }
  }

  private void processEndpoints(RideRequest req) {
    //  Normalize the endpoints
    req.setOrigin(mapService.getNormalizedAddress(req.getOrigin()));
    req.setDestination(mapService.getNormalizedAddress(req.getDestination()));

    //  Derive and cache the time (in seconds) and distance (in meters) for the primary route between these endpoints
    mapService.calcTimeAndDistance(req.getOrigin(), req.getDestination());
  }

}
