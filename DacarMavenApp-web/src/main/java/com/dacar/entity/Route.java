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

/**
 *
 * @author jonwetherbee
 */
@Entity
public class Route implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private short startZipCode;
  private int[] startLocation;
  private short endZipCode;
  private int[] endLocation;
  private int[][] path;

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
    if (!(object instanceof Route)) {
      return false;
    }
    Route other = (Route) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.dacar.entity.Route[ id=" + id + " ]";
  }

  /**
   * @return the startZipCode
   */
  public short getStartZipCode() {
    return startZipCode;
  }

  /**
   * @param startZipCode the startZipCode to set
   */
  public void setStartZipCode(short startZipCode) {
    this.startZipCode = startZipCode;
  }

  /**
   * @return the startLocation
   */
  public int[] getStartLocation() {
    return startLocation;
  }

  /**
   * @param startLocation the startLocation to set
   */
  public void setStartLocation(int[] startLocation) {
    this.startLocation = startLocation;
  }

  /**
   * @return the endZipCode
   */
  public short getEndZipCode() {
    return endZipCode;
  }

  /**
   * @param endZipCode the endZipCode to set
   */
  public void setEndZipCode(short endZipCode) {
    this.endZipCode = endZipCode;
  }

  /**
   * @return the endLocation
   */
  public int[] getEndLocation() {
    return endLocation;
  }

  /**
   * @param endLocation the endLocation to set
   */
  public void setEndLocation(int[] endLocation) {
    this.endLocation = endLocation;
  }

  /**
   * @return the path
   */
  public int[][] getPath() {
    return path;
  }

  /**
   * @param path the path to set
   */
  public void setPath(int[][] path) {
    this.path = path;
  }

}
