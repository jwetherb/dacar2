/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dacar.entity;

/**
 *
 * @author Jon Wetherbee <jon.wetherbee@gmail.com>
 */
public interface QueryNames {

  public static final String RideRequest_findByKey = "RideRequest.findByKey";
  public static final String RideRequest_findByStatus = "RideRequest.findByStatus";
  public static final String RouteCompatibility_findByOrigin = "RouteCompatibility.findByOrigin";
  public static final String RouteCompatibility_findByWrapperEndpoints = "RouteCompatibility.findByWrapperEndpoints";
  public static final String RouteCompatibility_findByAllEndpoints = "RouteCompatibility.findByAllEndpoints";
  public static final String GeoTempData_findReqKeys = "GeoTempData.findReqKeys";
  public static final String GeoTempData_findByReqKey = "GeoTempData.findByReqKey";
  public static final String GeoTempData_findByXYTlTu = "GeoTempData.findByXYTlTu";
  public static final String GeoTempData_findByX = "GeoTempData.findByX";
  public static final String GeoTempData_findByY = "GeoTempData.findByY";
  public static final String GeoTempData_findByT = "GeoTempData.findByT";
  public static final String GeoTempRoutes_findByEndPoints = "GeoTempRoutes.findByEndPoints";
}
