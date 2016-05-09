/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dacar.service;

import com.dacar.entity.RideRequest;
import com.dacar.facade.RideRequestFacade;
import com.dacar.maps.dacarmaps.DacarMapService;
import com.dacar.util.DacarUtils;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;

/**
 *
 * @author Jon Wetherbee <jon.wetherbee@gmail.com>
 */
@Singleton
@LocalBean
public class TestService {

  @EJB
  private DacarMapService dacarMapService;

  @EJB
  private RideRequestFacade reqFacade;

  public void beginTestRequests() throws ParseException {
    String google = "1600 amphitheatre parkway mountain view";
    String oracle = "200 oracle parkway, redwood shores";

    String ashwood = "1301  ashwood court san mateo";
    String colgate = "510  colgate wy, san mateo";
    String amnesia = "853 valencia ave, san francisco";
    String chapel = "777 valencia st, san francisco";
    String foreignCinema = "2534 mission st, san francisco";
    String monksKettle = "3141 16th st, sf";

    int wait = 10000;
    int counter = 0;
    
    RideRequest a2g = createRideRequest(ashwood, google, "5-2-16 7:30 pdt", wait, counter++);
    RideRequest c2g = createRideRequest(colgate, google, "5-2-16 7:15 pdt", wait, counter++);
    RideRequest a2o = createRideRequest(amnesia, oracle, "5-2-16 7:00 pdt", wait, counter++);
    RideRequest c2o = createRideRequest(chapel, oracle, "5-2-16 7:15 pdt", wait, counter++);
    RideRequest f2o = createRideRequest(foreignCinema, oracle, "5-2-16 7:30 pdt", wait, counter++);
    RideRequest m2o = createRideRequest(monksKettle, oracle, "5-2-16 7:45 pdt", wait, counter++);
  }
  
  private RideRequest createRideRequest(String startLocation, String endLocation, String leaveBy, int wait, int counter)
          throws ParseException {
    final RideRequest req = new RideRequest();
    req.setReqKey("TEST" + counter);
    req.setStartLocation(dacarMapService.getNormalizedAddress(startLocation));
    req.setEndLocation(dacarMapService.getNormalizedAddress(endLocation));
    req.setDepartureTime(DacarUtils.formatDate(leaveBy));

    reqFacade.newRideRequest(req);

//    if (wait > 0) {
//      try {
//        Thread.currentThread().wait(wait);
//      } catch (InterruptedException ex) {
//        Logger.getLogger(TestService.class.getName()).log(Level.SEVERE, null, ex);
//      }
//    }

    return req;
  }

  public String dataDump() {
    StringBuilder data = new StringBuilder();
    
    //  DacarMapService.getAddressMap()
    Map<String,String> addressMap = dacarMapService.getAddressMap();
    for (Map.Entry<String,String> entry : addressMap.entrySet()) {
      System.out.println(entry.getKey() + " -> " + entry.getValue());
      data.append(entry.getKey() + " -> " + entry.getValue() + '\n');
    }
    
    List<RideRequest> reqs;
    
    dataDumpReqs(data, reqFacade.getNewRequests(), "NEW Requests:\n");
    dataDumpReqs(data, reqFacade.getUpdatedRequests(), "UPDATED Requests:\n");
    dataDumpReqs(data, reqFacade.getCancelledRequests(), "CANCELLED Requests:\n");
    
    return data.toString();
  }

  private void dataDumpReqs(StringBuilder data, List<RideRequest> reqs, String header) {
    //  RideRequestFacade.getNewRequests();
    data.append(header);
    for (RideRequest req : reqs) {
      data.append(req.getReqKey()+" ; START: "+req.getStartLocation()+" ; END: "+req.getEndLocation()+'\n');
    }
  }

  public void resetTestData() {
    dacarMapService.clearAddressMap();
    reqFacade.removeByReqKeyPattern("%");
  }
}
