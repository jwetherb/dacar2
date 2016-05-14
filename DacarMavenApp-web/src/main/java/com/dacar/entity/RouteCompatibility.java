/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dacar.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author jonwetherbee
 */
@Entity
@NamedQueries({
  @NamedQuery(name = QueryNames.RouteCompatibility_findByOrigin,
              query = "select o from RouteCompatibility o where o.origin = :origin"),
  @NamedQuery(name = QueryNames.RouteCompatibility_findByWrapperEndpoints,
              query = "select o from RouteCompatibility o where o.origin = :origin and o.destination = :destination"),
  @NamedQuery(name = QueryNames.RouteCompatibility_findByAllEndpoints,
              query = "select o from RouteCompatibility o where o.origin = :origin and o.destination = :destination and o.otherOrigin = :otherOrigin and o.otherDestination = :otherDestination")
})
@IdClass(RouteCompatibilityPK.class)
public class RouteCompatibility implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id  
  private String origin;
  @Id
  private String destination;
  @Id
  private String otherOrigin;
  @Id
  private String otherDestination;
  private long seconds;
  private long meters;

  public String getOrigin() {
    return origin;
  }

  public void setOrigin(String origin) {
    this.origin = origin;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (origin != null ? origin.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the origin fields are not set
    if (!(object instanceof RouteCompatibility)) {
      return false;
    }
    RouteCompatibility other = (RouteCompatibility) object;
    if ((this.origin == null && other.origin != null) || (this.origin != null && !this.origin.equals(other.origin))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.dacar.entity.RouteCompatibility[ id=" + origin + " ]";
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
   * @return the otherOrigin
   */
  public String getOtherOrigin() {
    return otherOrigin;
  }

  /**
   * @param otherOrigin the otherOrigin to set
   */
  public void setOtherOrigin(String otherOrigin) {
    this.otherOrigin = otherOrigin;
  }

  /**
   * @return the otherDestination
   */
  public String getOtherDestination() {
    return otherDestination;
  }

  /**
   * @param otherDestination the otherDestination to set
   */
  public void setOtherDestination(String otherDestination) {
    this.otherDestination = otherDestination;
  }

  /**
   * @return the seconds
   */
  public long getSeconds() {
    return seconds;
  }

  /**
   * @param seconds the seconds to set
   */
  public void setSeconds(long seconds) {
    this.seconds = seconds;
  }

  /**
   * @return the meters
   */
  public long getMeters() {
    return meters;
  }

  /**
   * @param meters the meters to set
   */
  public void setMeters(long meters) {
    this.meters = meters;
  }
  
}
