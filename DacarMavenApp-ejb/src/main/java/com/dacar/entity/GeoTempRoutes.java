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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author jonwetherbee
 */
@NamedQueries({
  @NamedQuery(name = QueryNames.GeoTempRoutes_findByEndPoints,
          query = "select o from GeoTempRoutes o where"
                  + " o.startGC_lat = :startGC_lat and"
                  + " o.startGC_lon = :startGC_lon and"
                  + " o.endGC_lat = :endGC_lat and"
                  + " o.endGC_lon = :endGC_lon")
})
@Entity
public class GeoTempRoutes implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private int startGC_lat; //  Starting latitude rounded to nearest xx
  private int startGC_lon; //  Starting longitude rounded to nearest xx
  private int endGC_lat; //  Ending latitude rounded to nearest xx
  private int endGC_lon; //  Ending longitude rounded to nearest xx
  private int[][][] routeGTRs; // [route#][vertex#][lat,lon,t_delta], where route# is in order of suitability (0==most preferred)

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof GeoTempRoutes)) {
      return false;
    }
    GeoTempRoutes other = (GeoTempRoutes) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.dacar.entity.GeoTempRoute[ id=" + id + " ]";
  }

  /**
   * @return the routeGTRs
   */
  public int[][][] getRouteGTRs() {
    return routeGTRs;
  }

  /**
   * @param routeGTRs the routeGTRs to set
   */
  public void setRouteGTRs(int[][][] routeGTRs) {
    this.routeGTRs = routeGTRs;
  }

  /**
   * @return the startGC_lat
   */
  public int getStartGC_lat() {
    return startGC_lat;
  }

  /**
   * @param startGC_lat the startGC_lat to set
   */
  public void setStartGC_lat(int startGC_lat) {
    this.startGC_lat = startGC_lat;
  }

  /**
   * @return the startGC_lon
   */
  public int getStartGC_lon() {
    return startGC_lon;
  }

  /**
   * @param startGC_lon the startGC_lon to set
   */
  public void setStartGC_lon(int startGC_lon) {
    this.startGC_lon = startGC_lon;
  }

  /**
   * @return the endGC_lat
   */
  public int getEndGC_lat() {
    return endGC_lat;
  }

  /**
   * @param endGC_lat the endGC_lat to set
   */
  public void setEndGC_lat(int endGC_lat) {
    this.endGC_lat = endGC_lat;
  }

  /**
   * @return the endGC_lon
   */
  public int getEndGC_lon() {
    return endGC_lon;
  }

  /**
   * @param endGC_lon the endGC_lon to set
   */
  public void setEndGC_lon(int endGC_lon) {
    this.endGC_lon = endGC_lon;
  }

}
