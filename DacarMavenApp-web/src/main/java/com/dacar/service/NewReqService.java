/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dacar.service;

import com.dacar.entity.RideRequest;
import com.dacar.entity.RideRequest.RiderType;
import static com.dacar.entity.RideRequest.RiderType.*;
import com.dacar.entity.RouteCompatibility;
import com.dacar.facade.RideRequestFacade;
import com.dacar.facade.UnmatchedPoolFacade;
import com.dacar.maps.dacarmaps.DacarMapService;
import java.util.ArrayList;
import java.util.List;
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
   * A NEW req comes in, and we want to check whether it is a good driver match another UNMATCHED req or another
   * UNMATCHED ride is a good driver match for it. In general, we only care how much one ride will get impacted by
   * detouring through another route. In some cases, a driver might detour through multiple routes: in sequence, nested,
   * interlaced:
   *
   * https://drive.google.com/drive/folders/0B33L_35NUbloT0h3TmxPTDVYRFU
   *
   * When calculating compatibility, we find individually compatible rides first, and then check whether it is possible
   * to pick up multiple passengers, in one of the sequences above, while still conforming to the time/distance
   * requirements of the driver.
   *
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

    //  Get the Routes for this request (key is endpoints: "[origin]->[destination]")
    processEndpoints(req);

    //  Generate the compatibility map for this request (key is endpoints: "[origin]->[destination]")
    //  with each of the UNMATCHED reqs
    List<RouteCompatibility> thisRouteCompats = new ArrayList<RouteCompatibility>();
    List<RouteCompatibility> otherRouteCompats = new ArrayList<RouteCompatibility>();
    final boolean canDrive = req.getRiderType() != RiderType.MUST_BE_PASSENGER;
    for (RideRequest uReq : reqFacade.getRequestsByStatus(RideRequest.RequestStatusType.UNMATCHED)) {

      //  Check how picking up the unmatched req's ride would affect this req's ride
      if (canDrive) {
        thisRouteCompats.add(mapService.calcCompatibility(req, uReq));
      }

      //  Also check how picking up this req's ride would affect the unmatched req's ride
      if (uReq.getRiderType() != RiderType.MUST_BE_PASSENGER) {
        otherRouteCompats.add(mapService.calcCompatibility(uReq, req));
      }
    }

    //  Do other stuff to try to find a match
    //  Given the base time/distance for this route, find compatible rides, in ascending order of additional
    //  time and distance
    thisRouteCompats = findCompatRoutes(req, thisRouteCompats);
    otherRouteCompats = findCompatRoutes(req, otherRouteCompats);

    //  If any compatible ride(s) are found, form a new Ride and set their status to MATCHED
    //  Else set status to UNMATCHED and save
    if (thisRouteCompats.size() > 0 || otherRouteCompats.size() > 0) {
      req.setStatus(RideRequest.RequestStatusType.MATCHED);
    } else {
      req.setStatus(RideRequest.RequestStatusType.UNMATCHED);
    }
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

  private List<RouteCompatibility> findCompatRoutes(RideRequest req, List<RouteCompatibility> routeCompats) {
    long baseRouteSeconds = mapService.calcTimeAndDistance(req.getOrigin(), req.getDestination())[0];
    int addlSecondsAccepted = 60 * req.getAdditionalMinutesAccepted();
    long totalSecondsAccepted = baseRouteSeconds + addlSecondsAccepted;

    System.out.println("Match(es) found for RideRequest: " + req.toString());
    List<RouteCompatibility> results = new ArrayList<RouteCompatibility>();
    for (RouteCompatibility routeCompat : routeCompats) {
      if (routeCompat.getSeconds() <= totalSecondsAccepted) {
        results.add(routeCompat);
        System.out.println("    " + routeCompat.toString());
      }
    }

    return results;
  }

}
