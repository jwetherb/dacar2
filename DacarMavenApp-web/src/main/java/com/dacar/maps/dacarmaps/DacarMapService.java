/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dacar.maps.dacarmaps;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;

/**
 *
 * @author jonwetherbee
 */
@Singleton
public class DacarMapService {

  private final GeoApiContext _context = new GeoApiContext().setApiKey("AIzaSyBYY2nX7-YT0gNuooW76qqV7hP-jKlXEUA");
  private final Map<String, String> addressMap = new HashMap();

  public String geoCode(String address) {
    GeocodingResult[] geocodes;
    try {
      geocodes = GeocodingApi.geocode(_context, address).await();
      if (geocodes.length == 0) {
        System.out.println("No geocodes were found for address: " + address + "!!!");
      }
      else if (geocodes.length > 1) {
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

}
