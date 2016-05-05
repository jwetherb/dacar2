/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dacar.resource;

import com.dacar.entity.RideRequest;
import com.dacar.facade.RideRequestFacade;
import com.dacar.service.RideRequestService;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Jon Wetherbee <jon.wetherbee@gmail.com>
 */
@Stateless
@Path("riderequest")
public class RideRequestREST {

  @EJB
  private RideRequestFacade facade;

  @EJB
  private RideRequestService service;

  public RideRequestREST() {
  }

  @POST
  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public RideRequest newRideRequest(RideRequest entity) {
    return facade.newRideRequest(entity);
  }

  @PUT
  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public RideRequest updateRideRequest(RideRequest entity) {
    return facade.updateRideRequest(entity);
  }

  @DELETE
  @Path("{reqKey}")
  public void remove(@PathParam("reqKey") String reqKey) {
    facade.cancelRideRequest(reqKey);
  }

  @GET
  @Path("{reqKey}")
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public RideRequest findByKey(@PathParam("reqKey") String reqKey) {
    return facade.find(reqKey);
  }

  @GET
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public List<RideRequest> findAll() {
    return facade.findAll();
  }

  @GET
  @Path("{from}/{to}")
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public List<RideRequest> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
    return facade.findRange(new int[]{from, to});
  }

  @GET
  @Path("count")
  @Produces(MediaType.TEXT_PLAIN)
  public String countREST() {
    return String.valueOf(facade.count());
  }
}
