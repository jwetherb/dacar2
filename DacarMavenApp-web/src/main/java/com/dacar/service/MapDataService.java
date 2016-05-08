/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dacar.service;

import com.dacar.entity.GeoTempData;
import com.dacar.entity.GeoTempRoutes;
import com.dacar.entity.RideRequest;
import com.dacar.facade.GeoTempDataFacade;
import com.dacar.facade.GeoTempRoutesFacade;
import com.dacar.maps.dacarmaps.DacarMapService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;

/**
 *
 * @author Jon Wetherbee <jon.wetherbee@gmail.com>
 */
@Singleton
public class MapDataService {

  /**
   * Use the source/destination in the RideRequest to calculate the geo-temporal data points that comprise the available
   * routes that satisfy this request.
   *
   * @param req the RideRequest
   * @return an int[][][], with minimum dimensions [1][2][x,y,t]. - First dimension is the route (minimum size 1, but
   * Google could suggest many routes), - Second dimension is the point number along the route (mininum size 2, for
   * source and destination), - Third dimension is the [x,y,t] coordinates for each point along that route (always size
   * 3).
   */
  @EJB
  private GeoTempDataFacade gtdFacade;

  @EJB
  private GeoTempRoutesFacade gtrFacade;

  private Set reqKeys = new HashSet<String>();

  //  For use by dummy GTD generator
  private int route = 0;
  private int rider = 0;
  private Random random = new Random();
  private int[] coords = genNewDataPoint();

  @PostConstruct
  void init() {
    if (gtdFacade != null) {
      List<String> alreadyCalcdReqKeys = gtdFacade.getReqKeys();

      reqKeys.addAll(alreadyCalcdReqKeys);
    }
  }

  private GeoTempRoutes assignGTRs(RideRequest req) {
    
    DacarMapService dms = new DacarMapService();
    
    String result = dms.testme();
    System.out.println("result");
    
    GeoTempRoutes gtrs = req.getRoutes();

    //  Request already knows its routes
    if (gtrs != null) {
      return gtrs;
    }

//    if (reqKeys.contains(req.getReqKey())) {
//      return gtdFacade.getByReqKey(req.getReqKey());
//    }

    //  Look up the route by start/end coords. We cache all routes we've been given by Google
    //  so we should never have to derive the same route twice.
//    gtrs = gtrFacade.getGTRByEndPoints(req.getStartLocation()[0], req.getStartLocation()[1], 
//            req.getEndLocation()[0], req.getEndLocation()[1]);

    //  TO DO: Get the real data from Google somehow. For now, this is just
    //  a dummy data generator. It cycles slight variances of A, B, and C
    //  routes, with three requests along each route. Then moves on to D, E,
    //  and F, and so on.
    int[][][] geoTempData = new int[1][5][3];
    for (int i = 0; i < 5; i++) {
      int x = (rider == 1 && i == 0) ? 1 : 0;
      int y = (rider == 2 && i == 1) ? 1 : 0;
      geoTempData[0][i] = new int[]{coords[0] + x, coords[1] + y, coords[2] + i};
    }
    if (++route % 3 == 0 && rider == 2) {
      route += 3;
    }
    if (++rider == 3) {
      rider = 0;
    }
    //  End dummy GTD generator

    GeoTempData gtd;
    List<GeoTempData> gtdList = new ArrayList<GeoTempData>();
    for (int route = 0; route < geoTempData.length; route++) {
      for (int point = 0; point < geoTempData[route].length; point++) {
        gtd = new GeoTempData();
        gtd.setReqKey(req.getReqKey());
        gtd.setX(geoTempData[route][point][0]);
        gtd.setY(geoTempData[route][point][1]);
        gtd.setT_preferred(geoTempData[route][point][2]);
        gtd.setT_lower(geoTempData[route][point][2] - req.getDepartureBeforeMins());
        gtd.setT_upper(geoTempData[route][point][2] + req.getDepartureAfterMins());
        gtdFacade.create(gtd);
        gtdList.add(gtd);
      }
    }

    reqKeys.add(req.getReqKey());

    return gtrs;
  }

  private int[] genNewDataPoint() {
    return new int[]{random.nextInt(100), random.nextInt(100), random.nextInt(100)};
  }

  public Set<String> getGTDMatches(RideRequest req) {

    assignGTRs(req);

    //  Here's where the rubber hits the road. We start with a req and its
    //  GTD coords, and we try to match it with other rides in the 
    //  Unmatched list.
    Set<String> matchingReqKeys = new HashSet<String>();
    List<GeoTempData> myGtds = gtdFacade.getByReqKey(req.getReqKey());
    if (myGtds != null) {
      for (GeoTempData myGtd : myGtds) {
        List<String> reqKeys = gtdFacade.getReqKeysByXYT(myGtd.getX(),
                myGtd.getY(),
                myGtd.getT_preferred() - myGtd.getT_lower(),
                myGtd.getT_preferred() + myGtd.getT_upper());
        matchingReqKeys.addAll(reqKeys);
      }
    }
    matchingReqKeys.remove(req.getReqKey());

    return matchingReqKeys;
  }

  /**
   * Look up this req's start/end GCs in the GEO_ROUTES table. 
   * 
   * If not found, we need to create an entry for this GR.
   * 
   * Ask Google for the preferred and alternate routes between these start/end GCs, and store this as an int [][][]
   * array in the GEO_ROUTES table.
   * 
   * Derive the list of compatible GRs already known to the system, and for each, store the GR_ID and the minutes that
   * route adds to this on. Store these values in ascending order by minutes. Since each GR may have multiple paths
   * @param req 
   */
  public void processGR(RideRequest req) {
    
  }
}
