/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dacar.maps.dacarmaps;

import com.dacar.entity.RideRequest;
import com.dacar.entity.RouteCompatibility;
import com.dacar.facade.RouteCompatibilityFacade;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.GeocodingResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.persistence.NoResultException;

/**
 *
 * @author jonwetherbee
 */
@Singleton
public class DacarMapService {

  private final GeoApiContext _context = new GeoApiContext().setApiKey("AIzaSyBYY2nX7-YT0gNuooW76qqV7hP-jKlXEUA");
  /**
   * key: user-provided address; value: normalized address
   */
  private final Map<String, String> addressMap = new HashMap();
  /**
   * key: endpoints; value: long [] {seconds, meters}
   */
  private final Map<String, long[]> timeDistanceMap = new HashMap();
  /**
   * key: endpoints1->endpoints2; value: long [] {seconds, meters}
   */
  private final Map<String, long[]> routeImpactMap = new HashMap();

  @EJB
  private RouteCompatibilityFacade routeCompatFacade;

  public String geoCode(String address) {
    GeocodingResult[] geocodes;
    try {
      geocodes = GeocodingApi.geocode(_context, address).await();
      if (geocodes.length == 0) {
        System.out.println("No geocodes were found for address: " + address + "!!!");
      } else if (geocodes.length > 1) {
        System.out.println("Multiple geocodes were found for address: " + address + "!!!");
      }
      return geocodes[0].formattedAddress;
    } catch (Exception ex) {
      Logger.getLogger(DacarMapService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return "NOT FOUND";
  }

  public String getNormalizedAddress(String address) {
    String a = addressMap.get(address);
    if (a == null) {
      a = geoCode(address);
      addressMap.put(address, a);
      addressMap.put(a, a);
    }

    return a;
  }

  public Map<String, String> getAddressMap() {
    return new HashMap<String, String>(addressMap);
  }

  public void clearAddressMap() {
    addressMap.clear();
  }

  public long[] calcTimeAndDistance(String origin, String destination) {
    String endpoints = RideRequest.getEndpoints(origin, destination);
    long[] result = timeDistanceMap.get(endpoints);
    if (result == null) {
      DirectionsApiRequest dirReq = DirectionsApi
          .newRequest(_context)
          .origin(origin)
          .destination(destination)
          .alternatives(false);
      try {
        DirectionsResult directions = dirReq.await();
        //  Sum the time and distance for each leg
        long seconds = 0;
        long meters = 0;
        for (int i = 0; i < directions.routes[0].legs.length; i++) {
          seconds += directions.routes[0].legs[i].duration.inSeconds;
          meters += directions.routes[0].legs[i].distance.inMeters;
        }
        result = new long[]{seconds, meters};
        timeDistanceMap.put(endpoints, result);
      } catch (Exception ex) {
        Logger.getLogger(DacarMapService.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

    return result;
  }

  public RouteCompatibility calcCompatibility(RideRequest req, RideRequest otherReq) {
    RouteCompatibility routeCompat = null;
    List<RouteCompatibility> routeCompats = routeCompatFacade.findByAllEndpoints(req.getOrigin(), req.getDestination(), otherReq.getOrigin(), otherReq.getDestination());
    if (routeCompats != null && !routeCompats.isEmpty()) {
      routeCompat = routeCompats.get(0);
    } else {
      routeCompat = new RouteCompatibility();
      routeCompat.setOrigin(req.getOrigin());
      routeCompat.setDestination(req.getDestination());
      routeCompat.setOtherOrigin(otherReq.getOrigin());
      routeCompat.setOtherDestination(otherReq.getDestination());

      long seconds = 0;
      long meters = 0;
      long[] result;
      result = calcTimeAndDistance(routeCompat.getOrigin(), routeCompat.getOtherOrigin());
      seconds += result[0];
      meters += result[1];
      result = calcTimeAndDistance(routeCompat.getOtherOrigin(), routeCompat.getOtherDestination());
      seconds += result[0];
      meters += result[1];
      result = calcTimeAndDistance(routeCompat.getOtherDestination(), routeCompat.getDestination());
      seconds += result[0];
      meters += result[1];

      routeCompat.setSeconds(seconds);
      routeCompat.setMeters(meters);

      routeCompatFacade.create(routeCompat);
    }

    return routeCompat;
  }

}
