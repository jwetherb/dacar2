/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dacar.maps.dacarmaps;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.PendingResult;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.GeocodingResult;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import javax.ws.rs.client.ClientBuilder;
import org.junit.Test;

/**
 *
 * @author jonwetherbee
 */
public class DacarMapServiceTest {

  private final GeoApiContext _context = new GeoApiContext().setApiKey("AIzaSyBYY2nX7-YT0gNuooW76qqV7hP-jKlXEUA");

  /**
   * @param args the command line arguments
   */
  @Test
  public void mainTest() {
    final DacarMapService dms = new DacarMapService();
    try {
      String inputDate = "05-02-2016 7:30 pdt";
      SimpleDateFormat input = new SimpleDateFormat("M-d-yy H:mm z", Locale.US);
      SimpleDateFormat output = new SimpleDateFormat("EEE MMM dd hh:mm a z yyyy", Locale.US);
      Date date = input.parse(inputDate);
      String text = output.format(date);
      System.out.println(text);
      //LocalDate parsedDate = LocalDate.parse(text, formatter);

      testme(dms);
    } catch (ParseException ex) {
      Logger.getLogger(DacarMapServiceTest.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public String testme(DacarMapService dms) {

    String google = "1600 Amphitheatre Parkway Mountain View, CA 94043";
    String ashwood = "1301  Ashwood Court San Mateo, CA 94402";
    String oracle = "200 Oracle Parkway, Redwood Shores, CA 94065";

    google = dms.geoCode(google);
    System.out.println(ashwood);
    ashwood = dms.geoCode(ashwood);
    System.out.println(ashwood);
    System.out.println(oracle);
    oracle = dms.geoCode(oracle);
    System.out.println(oracle);

    DirectionsApiRequest req = DirectionsApi
            .newRequest(_context)
            .origin(ashwood)
            .destination(oracle)
            .alternatives(true);

    getDirectionsSynch(req);

    req = DirectionsApi
            .newRequest(_context)
            .origin(ashwood)
            .destination(oracle)
            .alternatives(true);
    getDirectionsAsynch(req);
    System.out.println("Done");

//    String clientID = null;
//    String clientSecret = null;
//
//    GeoApiContext _context = new GeoApiContext().setEnterpriseCredentials(clientID, clientSecret);
//    GeocodingResult[] results = GeocodingApi.geocode(_context,
//            "1600 Amphitheatre Parkway Mountain View, CA 94043").await();
//    System.out.println(results[0].formattedAddress);
//    GeocodingApiRequest req = GeocodingApi.newRequest(_context).address("Sydney");
//    try {
//      req.await();
//      // Handle successful request.
//    } catch (Exception e) {
//      // Handle error
//    }
    String baseUrl = "https://maps.googleapis.com/";
    String mapApi = "maps/api/directions/json";
    String param1 = "origin";
    String args1 = "1301%20Ashwood%20Ct,%20San%20Mateo,%20CA";
    String param2 = "destination";
    String args2 = "200%20Oracle%20Pkwy,%20Redwood%20Shores,%20CA";
    String directionsResult = ClientBuilder.newClient()
            .target(baseUrl).path(mapApi)
            .queryParam(param1, args1)
            .queryParam(param2, args2)
            .request().get(String.class);
    System.out.println(directionsResult);

    return "Success!!";
  }

  private void getDirectionsSynch(DirectionsApiRequest req) {
    try {
      DirectionsResult directions = req.await();
      for (int i = 0; i < directions.routes.length; i++) {
        System.out.println(directions.routes[i].summary);
        System.out.println(directions.routes[i].legs[0].steps[0].htmlInstructions);
      }
    } catch (Exception ex) {
      Logger.getLogger(DacarMapServiceTest.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private void getDirectionsAsynch(DirectionsApiRequest req) {
    req.setCallback(new PendingResult.Callback<DirectionsResult>() {
      @Override
      public void onResult(DirectionsResult directions) {
        for (int i = 0; i < directions.routes.length; i++) {
          System.out.println(directions.routes[i].legs[0].steps[0].htmlInstructions);
        }
      }

      @Override
      public void onFailure(Throwable e) {
        System.out.println("ERROR: Failure when ");
      }
    });
  }
}
