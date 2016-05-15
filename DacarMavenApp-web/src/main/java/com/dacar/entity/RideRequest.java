/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dacar.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.*;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jon Wetherbee <jon.wetherbee@gmail.com>
 */
@Entity
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = QueryNames.RideRequest_findByKey,
              query = "select o from RideRequest o where o.reqKey = :reqKey"),
  @NamedQuery(name = QueryNames.RideRequest_findByStatus,
              query = "select o from RideRequest o where o.status = :status")
})
public class RideRequest implements Serializable {

  /**
   * NEW -- added through a NEW rest call UPDATED -- added through an UPDATE rest call CANCELLED -- added through a
   * CANCELLED rest call UNMATCHED -- processed from NEW or UPDATED, but not matched MATCHED -- processed from NEW or
   * UPDATED, and matched
   */
  public static enum RequestStatusType {
    NEW,
    UPDATED,
    CANCELLED,
    UNMATCHED,
    MATCHED
  }

  public static enum AtmosphereType {
    SOCIAL,
    QUIET
  }

  public static enum OptimizeType {
    LENGTH_OF_TRIP,
    OVERALL_COST,
    FEWEST_SEGMENTS,
    MINIMIZE_TRANSIT
  }

  public static enum RiderType {
    MUST_BE_DRIVER,
    PREFER_TO_BE_DRIVER,
    CAN_BE_DRIVER_OR_PASSENGER,
    PREFER_TO_BE_PASSENGER,
    MUST_BE_PASSENGER
  }

  private static final long serialVersionUID = 1L;
  @Id
  private String reqKey;
  @Temporal(TIMESTAMP)
  private Date createDate;
  private RequestStatusType status = RequestStatusType.NEW;
  /**
   * Map of reqKey->[thisRouteNum,otherRouteNum,time,distance] where a routeNum is the index that specifies which of
   * possibly multiple routes
   */
  private String reqHandler;
  @ManyToOne
  private Rider rider;
  @ManyToOne
  private Ride ride;
  @ManyToOne
  private GeoTempRoutes routes;
  private RiderType riderType = RiderType.CAN_BE_DRIVER_OR_PASSENGER;
  private String origin;
  private int acceptableStartRadius;
  private String destination;
  private int acceptableEndRadius;
  @Temporal(TIMESTAMP)
  private Date departureTime;
  private int departureBeforeMins;
  private int departureAfterMins;
  @Temporal(TIMESTAMP)
  private Date arrivalTime;
  private int arrivalBeforeMins;
  private int arrivalAfterMins;
  private int additionalMinutesAccepted;
  private boolean multilegDirect;
  private int multilegNumSegments;
  private boolean multilegUseTransit;
  private OptimizeType optimizeBy = OptimizeType.LENGTH_OF_TRIP;
  private AtmosphereType atmosphere = AtmosphereType.QUIET;
  @ManyToOne
  private Vehicle driverVehicle;

  /**
   * @return the reqKey
   */
  public String getReqKey() {
    return reqKey;
  }

  /**
   * @param reqKey the reqKey to set
   */
  public void setReqKey(String reqKey) {
    this.reqKey = reqKey;
  }

  /**
   * @return the createDate
   */
  public Date getCreateDate() {
    return createDate;
  }

  /**
   * @param createDate the createDate to set
   */
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  /**
   * @return the status
   */
  public RequestStatusType getStatus() {
    return status;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(RequestStatusType status) {
    this.status = status;
  }

  /**
   * @return the reqHandler
   */
  public String getReqHandler() {
    return reqHandler;
  }

  /**
   * @param reqHandler the reqHandler to set
   */
  public void setReqHandler(String reqHandler) {
    this.reqHandler = reqHandler;
  }

  /**
   * @return the multilegNumSegments
   */
  public int getMultilegNumSegments() {
    return multilegNumSegments;
  }

  /**
   * @param multilegNumSegments the multilegNumSegments to set
   */
  public void setMultilegNumSegments(int multilegNumSegments) {
    this.multilegNumSegments = multilegNumSegments;
  }

  /**
   * @return the rider
   */
  public Rider getRider() {
    return rider;
  }

  /**
   * @param rider the rider to set
   */
  public void setRider(Rider rider) {
    this.rider = rider;
  }

  /**
   * @return the ride
   */
  public Ride getRide() {
    return ride;
  }

  /**
   * @param ride the ride to set
   */
  public void setRide(Ride ride) {
    this.ride = ride;
  }

  /**
   * @return the routes
   */
  public GeoTempRoutes getRoutes() {
    return routes;
  }

  /**
   * @param routes the routes to set
   */
  public void setRoutes(GeoTempRoutes routes) {
    this.routes = routes;
  }

  /**
   * @return the riderType
   */
  public RiderType getRiderType() {
    return riderType;
  }

  /**
   * @param riderType the riderType to set
   */
  public void setRiderType(RiderType riderType) {
    this.riderType = riderType;
  }

  /**
   * @return the origin
   */
  public String getOrigin() {
    return origin;
  }

  /**
   * @param origin the origin to set
   */
  public void setOrigin(String origin) {
    this.origin = origin;
  }

  /**
   * @return the acceptableStartRadius
   */
  public int getAcceptableStartRadius() {
    return acceptableStartRadius;
  }

  /**
   * @param acceptableStartRadius the acceptableStartRadius to set
   */
  public void setAcceptableStartRadius(int acceptableStartRadius) {
    this.acceptableStartRadius = acceptableStartRadius;
  }

  /**
   * @return the destination
   */
  public String getDestination() {
    return destination;
  }

  /**
   * @param destination the destination to set
   */
  public void setDestination(String destination) {
    this.destination = destination;
  }

  /**
   * @return the acceptableEndRadius
   */
  public int getAcceptableEndRadius() {
    return acceptableEndRadius;
  }

  /**
   * @param acceptableEndRadius the acceptableEndRadius to set
   */
  public void setAcceptableEndRadius(int acceptableEndRadius) {
    this.acceptableEndRadius = acceptableEndRadius;
  }

  /**
   * @return the departureTime
   */
  public Date getDepartureTime() {
    return departureTime;
  }

  /**
   * @param departureTime the departureTime to set
   */
  public void setDepartureTime(Date departureTime) {
    this.departureTime = departureTime;
  }

  /**
   * @return the departureBeforeMins
   */
  public int getDepartureBeforeMins() {
    return departureBeforeMins;
  }

  /**
   * @param departureBeforeMins the departureBeforeMins to set
   */
  public void setDepartureBeforeMins(int departureBeforeMins) {
    this.departureBeforeMins = departureBeforeMins;
  }

  /**
   * @return the departureAfterMins
   */
  public int getDepartureAfterMins() {
    return departureAfterMins;
  }

  /**
   * @param departureAfterMins the departureAfterMins to set
   */
  public void setDepartureAfterMins(int departureAfterMins) {
    this.departureAfterMins = departureAfterMins;
  }

  /**
   * @return the arrivalTime
   */
  public Date getArrivalTime() {
    return arrivalTime;
  }

  /**
   * @param arrivalTime the arrivalTime to set
   */
  public void setArrivalTime(Date arrivalTime) {
    this.arrivalTime = arrivalTime;
  }

  /**
   * @return the arrivalBeforeMins
   */
  public int getArrivalBeforeMins() {
    return arrivalBeforeMins;
  }

  /**
   * @param arrivalBeforeMins the arrivalBeforeMins to set
   */
  public void setArrivalBeforeMins(int arrivalBeforeMins) {
    this.arrivalBeforeMins = arrivalBeforeMins;
  }

  /**
   * @return the arrivalAfterMins
   */
  public int getArrivalAfterMins() {
    return arrivalAfterMins;
  }

  /**
   * @param arrivalAfterMins the arrivalAfterMins to set
   */
  public void setArrivalAfterMins(int arrivalAfterMins) {
    this.arrivalAfterMins = arrivalAfterMins;
  }

  /**
   * @return the additionalMinutesAccepted
   */
  public int getAdditionalMinutesAccepted() {
    return additionalMinutesAccepted;
  }

  /**
   * @param additionalMinutesAccepted the additionalMinutesAccepted to set
   */
  public void setAdditionalMinutesAccepted(int additionalMinutesAccepted) {
    this.additionalMinutesAccepted = additionalMinutesAccepted;
  }

  /**
   * @return the multilegDirect
   */
  public boolean isMultilegDirect() {
    return multilegDirect;
  }

  /**
   * @param multilegDirect the multilegDirect to set
   */
  public void setMultilegDirect(boolean multilegDirect) {
    this.multilegDirect = multilegDirect;
  }

  /**
   * @return the multilegUseTransit
   */
  public boolean isMultilegUseTransit() {
    return multilegUseTransit;
  }

  /**
   * @param multilegUseTransit the multilegUseTransit to set
   */
  public void setMultilegUseTransit(boolean multilegUseTransit) {
    this.multilegUseTransit = multilegUseTransit;
  }

  /**
   * @return the optimizeBy
   */
  public OptimizeType getOptimizeBy() {
    return optimizeBy;
  }

  /**
   * @param optimizeBy the optimizeBy to set
   */
  public void setOptimizeBy(OptimizeType optimizeBy) {
    this.optimizeBy = optimizeBy;
  }

  /**
   * @return the atmosphere
   */
  public AtmosphereType getAtmosphere() {
    return atmosphere;
  }

  /**
   * @param atmosphere the atmosphere to set
   */
  public void setAtmosphere(AtmosphereType atmosphere) {
    this.atmosphere = atmosphere;
  }

  /**
   * @return the driverVehicle
   */
  public Vehicle getDriverVehicle() {
    return driverVehicle;
  }

  /**
   * @param driverVehicle the driverVehicle to set
   */
  public void setDriverVehicle(Vehicle driverVehicle) {
    this.driverVehicle = driverVehicle;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (reqKey != null ? reqKey.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the reqKey fields are not set
    if (!(object instanceof RideRequest)) {
      return false;
    }
    RideRequest other = (RideRequest) object;
    if ((this.reqKey == null && other.reqKey != null) || (this.reqKey != null && !this.reqKey.equals(other.reqKey))) {
      return false;
    }
    return true;
  }

  @Transient
  public String getEndpoints() {
    return getEndpoints(getOrigin(), getDestination());
  }

  @Transient
  public static String getEndpoints(String origin, String destination) {
    return origin + "->" + destination;
  }

  @Override
  public String toString() {
    return "Origin: " + origin + "; Destination: " + destination;
  }

}
