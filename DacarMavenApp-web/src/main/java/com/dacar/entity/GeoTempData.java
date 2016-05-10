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
 * @author Jon Wetherbee <jon.wetherbee@gmail.com>
 */
@NamedQueries({
  @NamedQuery(name = QueryNames.GeoTempData_findByReqKey,
              query = "select o from GeoTempData o where o.reqKey = :reqKey"),
  @NamedQuery(name = QueryNames.GeoTempData_findReqKeys,
              query = "select distinct(o.reqKey) from GeoTempData o"),
  @NamedQuery(name = QueryNames.GeoTempData_findByXYTlTu,
              query = "select distinct(o.reqKey) from GeoTempData o where o.x = :x and o.y = :y and :t_lower < (o.t_preferred + o.t_upper) and :t_upper > (o.t_preferred - o.t_lower)"),
  @NamedQuery(name = QueryNames.GeoTempData_findByX,
              query = "select distinct(o.reqKey) from GeoTempData o where o.x = :x"),
  @NamedQuery(name = QueryNames.GeoTempData_findByY,
              query = "select distinct(o.reqKey) from GeoTempData o where o.y = :y"),
  @NamedQuery(name = QueryNames.GeoTempData_findByT,
              query = "select distinct(o.reqKey) from GeoTempData o where :t between o.t_lower and o.t_upper"),})
@Entity
public class GeoTempData implements Serializable {

  private static long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String reqKey;
  private int seqnum;
  private int x;
  private int y;
  private int t_preferred;
  private int t_lower;
  private int t_upper;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getReqKey() {
    return reqKey;
  }

  public void setReqKey(String reqKey) {
    this.reqKey = reqKey;
  }

  /**
   * @return the seqnum
   */
  public int getSeqnum() {
    return seqnum;
  }

  /**
   * @param seqnum the seqnum to set
   */
  public void setSeqnum(int seqnum) {
    this.seqnum = seqnum;
  }

  /**
   * @return the x
   */
  public int getX() {
    return x;
  }

  /**
   * @param x the x to set
   */
  public void setX(int x) {
    this.x = x;
  }

  /**
   * @return the y
   */
  public int getY() {
    return y;
  }

  /**
   * @param y the y to set
   */
  public void setY(int y) {
    this.y = y;
  }

  /**
   * @return the t_preferred
   */
  public int getT_preferred() {
    return t_preferred;
  }

  /**
   * @param t_preferred the t_preferred to set
   */
  public void setT_preferred(int t_preferred) {
    this.t_preferred = t_preferred;
  }

  /**
   * @return the t_lower
   */
  public int getT_lower() {
    return t_lower;
  }

  /**
   * @param t_lower the t_lower to set
   */
  public void setT_lower(int t_lower) {
    this.t_lower = t_lower;
  }

  /**
   * @return the t_upper
   */
  public int getT_upper() {
    return t_upper;
  }

  /**
   * @param t_upper the t_upper to set
   */
  public void setT_upper(int t_upper) {
    this.t_upper = t_upper;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (getId() != null ? getId().hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the reqKey fields are not set
    if (!(object instanceof GeoTempData)) {
      return false;
    }
    GeoTempData other = (GeoTempData) object;
    if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.reqKey.equals(other.reqKey))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.dacar.entity.GeoTempData[ id=" + getId() + " ]";
  }

}
