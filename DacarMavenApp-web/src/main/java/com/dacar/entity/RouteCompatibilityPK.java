/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dacar.entity;

import java.io.Serializable;
import javax.persistence.Id;

/**
 *
 * @author jonwetherbee
 */
public class RouteCompatibilityPK implements Serializable {

  @Id
  private String origin;
  @Id
  private String destination;
  @Id
  private String otherOrigin;
  @Id
  private String otherDestination;

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (origin != null ? origin.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the origin fields are not set
    if (!(object instanceof RouteCompatibilityPK)) {
      return false;
    }
    RouteCompatibilityPK other = (RouteCompatibilityPK) object;
    if ((this.origin == null && other.origin != null) || (this.origin != null && !this.origin.equals(other.origin))) {
      return false;
    }
    return true;
  }

}
