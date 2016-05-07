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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jon Wetherbee <jon.wetherbee@gmail.com>
 */
@Entity
@XmlRootElement
public class Vehicle implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String make;
  private String model;
  private int year_;
  private int numPassengers;
  private boolean hasWifi;

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
    if (!(object instanceof Vehicle)) {
      return false;
    }
    Vehicle other = (Vehicle) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.dacar.entity.Vehicle[ id=" + id + " ]";
  }

  /**
   * @return the make
   */
  public String getMake() {
    return make;
  }

  /**
   * @param make the make to set
   */
  public void setMake(String make) {
    this.make = make;
  }

  /**
   * @return the model
   */
  public String getModel() {
    return model;
  }

  /**
   * @param model the model to set
   */
  public void setModel(String model) {
    this.model = model;
  }

  /**
   * @return the year_
   */
  public int getYear_() {
    return year_;
  }

  /**
   * @param year_ the year_ to set
   */
  public void setYear_(int year_) {
    this.year_ = year_;
  }

  /**
   * @return the numPassengers
   */
  public int getNumPassengers() {
    return numPassengers;
  }

  /**
   * @param numPassengers the numPassengers to set
   */
  public void setNumPassengers(int numPassengers) {
    this.numPassengers = numPassengers;
  }

  /**
   * @return the hasWifi
   */
  public boolean isHasWifi() {
    return hasWifi;
  }

  /**
   * @param hasWifi the hasWifi to set
   */
  public void setHasWifi(boolean hasWifi) {
    this.hasWifi = hasWifi;
  }

}
