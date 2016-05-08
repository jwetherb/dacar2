/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dacar.service;

import com.dacar.entity.GeoTempData;
import com.dacar.entity.RideRequest;
import com.dacar.facade.GeoTempDataFacade;
import com.dacar.facade.RideRequestFacade;
import com.dacar.maps.dacarmaps.DacarMapService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jon Wetherbee <jon.wetherbee@gmail.com>
 */
@Singleton
@LocalBean
public class TestService {

  @EJB
  private DacarMapService dacarMapService;

  @PersistenceContext(unitName = "DacarWebModulePU")
  private EntityManager em;

  @EJB
  private RideRequestFacade reqFacade;

  @EJB
  private GeoTempDataFacade gtdFacade;

  public void resetTestData() {
    System.out.println("Whatever");
    
//    //  Delete existing test data
//    em.createQuery("delete from RideRequest o where o.reqKey like 'Test%'").executeUpdate();
//    em.createQuery("delete from GeoTempData o where o.reqKey like 'Test%'").executeUpdate();
//
//    //  Add four requests: H1-H4, all going to W1
//    RideRequest req;
//    GeoTempData gtd;
//    for (int i = 0; i < 4; i++) {
//      //  RideRequest
//      req = new RideRequest();
//
//      req.setReqKey("Test" + i);
//      if (i == 0) {
//        req.setStartLocation(new int[]{2, 2});
//        int[][] path = getPath(new int[][]{{2, 2}, {2, 6}, {15, 6}, {15, 1}});
//        int t_start = 5;
//        addGTDs(path, req.getReqKey(), i, t_start);
//      } else if (i == 1) {
//        req.setStartLocation(new int[]{1, 4});
//        int[][] path = getPath(new int[][]{{1, 4}, {1, 6}, {15, 6}, {15, 1}});
//        int t_start = 6;
//        addGTDs(path, req.getReqKey(), i, t_start);
//      } else if (i == 2) {
//        req.setStartLocation(new int[]{5, 1});
//        int[][] path = getPath(new int[][]{{5, 1}, {10, 1}, {10, 6}, {15, 6}, {15, 1}});
//        int t_start = 100;
//        addGTDs(path, req.getReqKey(), i, t_start);
//      } else if (i == 3) {
//        req.setStartLocation(new int[]{5, 4});
//        int[][] path = getPath(new int[][]{{5, 4}, {10, 4}, {10, 6}, {15, 6}, {15, 1}});
//        int t_start = 102;
//        addGTDs(path, req.getReqKey(), i, t_start);
//      }
//      req.setEndLocation(new int[]{15, 1});
//      reqFacade.create(req);
//
//    }

  }

  private void addGTDs(int[][] path, String reqKey, int seqnum, int t_start) {
    for (int t = 0; t < path.length; t++) {
      GeoTempData gtd = new GeoTempData();
      gtd.setReqKey(reqKey);
      gtd.setSeqnum(seqnum);
      gtd.setX(path[t][0]);
      gtd.setY(path[t][1]);
      gtd.setT_preferred(t + t_start);
      gtd.setT_lower(5);
      gtd.setT_upper(5);
      gtdFacade.create(gtd);
    }
  }

  private int[][] getPath(int[][] v) {
    List<Integer> xs = new ArrayList<Integer>();
    List<Integer> ys = new ArrayList<Integer>();
    int x = 0;
    int y = 0;
    for (int i = 0; i < v.length - 1; i++) {
      x = v[i][0];
      y = v[i][1];
      int dx = v[i + 1][0] - x;
      int dy = v[i + 1][1] - y;
      while (dx != 0 || dy != 0) {
        xs.add(x);
        ys.add(y);
        if (dx != 0) {
          if (dx > 0) {
            x++;
            dx--;
          } else {
            x--;
            dx++;
          }
        }
        if (dy != 0) {
          if (dy > 0) {
            y++;
            dy--;
          } else {
            y--;
            dy++;
          }
        }
      }
    }
    xs.add(x);
    ys.add(y);

    int[][] points = new int[xs.size()][2];
    for (int i = 0; i < xs.size(); i++) {
      points[i][0] = xs.get(i);
      points[i][1] = ys.get(i);
    }

    return points;
  }

  public void beginTestRequests() {
    String google = "1600 Amphitheatre Parkway Mountain View, CA 94043";
    String oracle = "200 Oracle Parkway, Redwood Shores, CA 94065";

    String ashwood = "1301  Ashwood Court San Mateo, CA 94402";
    String colgate = "510  colgate wy, san mateo";
    String amnesia = "Amnesia, Valencia Ave, San Francisco CA";
    String chapel = "The Chapel, Valencia St, San Francisco";
    String foreignCinema = "Foreign Cinema, San Francisco, CA";
    String monksCellar = "The Monk's Cellar, San Francisco, CA";

    RideRequest req;

    RideRequest a2g = createRideRequest(ashwood, google, "5-2-16 7:30 am pdt");
    RideRequest c2g = createRideRequest(colgate, google, "5-2-16 7:15 am pdt");
    RideRequest a2o = createRideRequest(amnesia, oracle, "5-2-16 7:00 am pdt");
    RideRequest c2o = createRideRequest(chapel, oracle, "5-2-16 7:15 am pdt");
    RideRequest f2o = createRideRequest(foreignCinema, oracle, "5-2-16 7:30 am pdt");
    RideRequest m2o = createRideRequest(monksCellar, oracle, "5-2-16 7:45 am pdt");
  }

  private RideRequest createRideRequest(String startLocation, String endLocation, String leaveBy) {
    final RideRequest req = new RideRequest();
    req.setStartLocation(formatLocation(startLocation));
    req.setEndLocation(formatLocation(endLocation));
    req.setDepartureTime(dacarMapService.formatDate(leaveBy));

    reqFacade.create(req);

    return req;
  }

  private String formatLocation(String loc) {
    DacarMapService mapService = new DacarMapService();
    return mapService.geoCode(loc);
  }
}
