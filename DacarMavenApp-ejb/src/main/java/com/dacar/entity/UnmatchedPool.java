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
import javax.persistence.OneToOne;

/**
 *
 * @author Jon Wetherbee <jon.wetherbee@gmail.com>
 */
@Entity
public class UnmatchedPool implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  private String reqKey;
  private int[][][] geoTempData;
  private String rideRequestJSON;

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (reqKey != null ? reqKey.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the reqKey fields are not set
    if (!(object instanceof UnmatchedPool)) {
      return false;
    }
    UnmatchedPool other = (UnmatchedPool) object;
    if ((this.reqKey == null && other.reqKey != null) || (this.reqKey != null && !this.reqKey.equals(other.reqKey))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.dacar.entity.UnmatchedPool[ reqKey=" + reqKey + " ]";
  }

  /**
   * @return the rideRequestJSON
   */
  public String getRideRequestJSON() {
    return rideRequestJSON;
  }

  /**
   * @param rideRequestJSON the rideRequestJSON to set
   */
  public void setRideRequestJSON(String rideRequestJSON) {
    this.rideRequestJSON = rideRequestJSON;
  }

  /**
   * @return the geoTempData
   */
  public int[][][] getGeoTempData() {
    return geoTempData;
  }

  /**
   * @param geoTempData the geoTempData to set
   */
  public void setGeoTempData(int[][][] geoTempData) {
    this.geoTempData = geoTempData;
  }

}
