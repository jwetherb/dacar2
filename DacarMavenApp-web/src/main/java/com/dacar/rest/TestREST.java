/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dacar.rest;

import com.dacar.service.RideRequestService;
import com.dacar.service.TestService;
import java.text.ParseException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 *
 * @author Jon Wetherbee <jon.wetherbee@gmail.com>
 */
@Stateless
@Path("test")
public class TestREST {

  @EJB
  private TestService testService;

  @EJB
  private RideRequestService reqService;

  public TestREST() {
  }

  @POST
  @Path("enableQueue")
  public void enableRideRequestService() {
    reqService.setEnabled(true);
  }

  @POST
  @Path("disableQueue")
  public void disableRideRequestService() {
    reqService.setEnabled(false);
  }

  @POST
  @Path("beginTestRequests")
  public void beginTestRequests() throws ParseException {
    testService.beginTestRequests();
  }

  @GET
  @Path("dataDump")
  public String dataDump() throws ParseException {
    return testService.dataDump();
  }

  @POST
  @Path("resetTestData")
  public void resetTestData() throws ParseException {
    testService.resetTestData();
  }

}
