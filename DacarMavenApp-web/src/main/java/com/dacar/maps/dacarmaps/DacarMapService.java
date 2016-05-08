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

/**
 *
 * @author jonwetherbee
 */
@Singleton
public class DacarMapService {

  private final GeoApiContext _context = new GeoApiContext().setApiKey("AIzaSyBYY2nX7-YT0gNuooW76qqV7hP-jKlXEUA");

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {

    try {
      String inputDate = "05-02-2016 7:30 pdt";
      SimpleDateFormat input = new SimpleDateFormat("M-d-yy H:mm z", Locale.US);
      SimpleDateFormat output = new SimpleDateFormat("EEE MMM dd hh:mm a z yyyy", Locale.US);
      Date date = input.parse(inputDate);
      String text = output.format(date);
      System.out.println(text);
      //LocalDate parsedDate = LocalDate.parse(text, formatter);

//      DacarMapService dms = new DacarMapService();
//
//      dms.testme();
    } catch (ParseException ex) {
      Logger.getLogger(DacarMapService.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public String testme() {

    String google = "1600 Amphitheatre Parkway Mountain View, CA 94043";
    String ashwood = "1301  Ashwood Court San Mateo, CA 94402";
    String oracle = "200 Oracle Parkway, Redwood Shores, CA 94065";

    google = geoCode(google);
    System.out.println(ashwood);
    ashwood = geoCode(ashwood);
    System.out.println(ashwood);
    System.out.println(oracle);
    oracle = geoCode(oracle);
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
//    System.out.println(directionsResult);

    return "Success!!";
  }

  public String geoCode(String address) {
    GeocodingResult[] geocodes;
    try {
      geocodes = GeocodingApi.geocode(_context, address).await();
      String result = "";
      for (int i = 0; i < geocodes.length; i++) {
        result += geocodes[i].formattedAddress;
      }
      return "" + geocodes.length + " result(s): " + result; // geocodes[0].formattedAddress;
    } catch (Exception ex) {
      Logger.getLogger(DacarMapService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return "NOT FOUND";
  }

  private void getDirectionsSynch(DirectionsApiRequest req) {
    try {
      DirectionsResult directions = req.await();
      for (int i = 0; i < directions.routes.length; i++) {
        System.out.println(directions.routes[i].summary);
        System.out.println(directions.routes[i].legs[0].steps[0].htmlInstructions);
      }
    } catch (Exception ex) {
      Logger.getLogger(DacarMapService.class.getName()).log(Level.SEVERE, null, ex);
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

  public Date formatDate(String inputDate) {
    try {
      SimpleDateFormat input = new SimpleDateFormat("M-d-yy H:mm z", Locale.US);
      SimpleDateFormat output = new SimpleDateFormat("EEE MMM dd hh:mm a z yyyy", Locale.US);
      Date date = input.parse(inputDate);
      String text = output.format(date);
      System.out.println(text);

      return date;

    } catch (ParseException ex) {
      Logger.getLogger(DacarMapService.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }
}
