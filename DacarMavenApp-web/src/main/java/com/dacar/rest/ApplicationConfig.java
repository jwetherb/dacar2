/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dacar.rest;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Jon Wetherbee <jon.wetherbee@gmail.com>
 */
@javax.ws.rs.ApplicationPath("rest")
public class ApplicationConfig extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> resources = new java.util.HashSet<>();
    addRestResourceClasses(resources);
    return resources;
  }

  /**
   * Do not modify addRestResourceClasses() method. It is automatically populated with all resources defined in the
   * project. If required, comment out calling this method in getClasses().
   */
  private void addRestResourceClasses(Set<Class<?>> resources) {
    resources.add(com.dacar.rest.RideRequestREST.class);
    resources.add(com.dacar.rest.RiderFacadeREST.class);
    resources.add(com.dacar.rest.TestREST.class);
    resources.add(com.dacar.rest.VehicleFacadeREST.class);
  }

}
