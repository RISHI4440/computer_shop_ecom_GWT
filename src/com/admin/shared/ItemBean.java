/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.shared;

import java.io.Serializable;
import java.util.Collection;

public class ItemBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer itemid;
    private String itemname;
    private String modelno;
    private String shortdesc;
    private String longdesc;
    private double price;
    private ItemtypeBean itemtypeid;
    private CompanyBean companyid;
    private Collection<OrderdetailBean> orderdetailCollection;

    public ItemBean() {
    }

    public ItemBean(Integer itemid) {
        this.itemid = itemid;
    }

    public ItemBean(Integer itemid, String itemname, double price) {
        this.itemid = itemid;
        this.itemname = itemname;
        this.price = price;
    }

    public Integer getItemid() {
        return itemid;
    }

    public void setItemid(Integer itemid) {
        this.itemid = itemid;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public void setShortdesc(String shortdesc) {
        this.shortdesc = shortdesc;
    }

    public String getLongdesc() {
        return longdesc;
    }

    public void setLongdesc(String longdesc) {
        this.longdesc = longdesc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ItemtypeBean getItemtypeid() {
        return itemtypeid;
    }

    public void setItemtypeid(ItemtypeBean itemtypeid) {
        this.itemtypeid = itemtypeid;
    }

    public CompanyBean getCompanyid() {
        return companyid;
    }

    public void setCompanyid(CompanyBean companyid) {
        this.companyid = companyid;
    }

    
    public Collection<OrderdetailBean> getOrderdetailCollection() {
        return orderdetailCollection;
    }

    public void setOrderdetailCollection(Collection<OrderdetailBean> orderdetailCollection) {
        this.orderdetailCollection = orderdetailCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemid != null ? itemid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemBean)) {
            return false;
        }
        ItemBean other = (ItemBean) object;
        if ((this.itemid == null && other.itemid != null) || (this.itemid != null && !this.itemid.equals(other.itemid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Item[ itemid=" + itemid + " ]";
    }
    
}
