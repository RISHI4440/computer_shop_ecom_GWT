/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.shared;

import java.io.Serializable;

public class OrderdetailBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer orderdetailid;
    private int quantity;
    private double price;
    private SorderBean orderid;
    private ItemBean itemid;

    public OrderdetailBean() {
    }

    public OrderdetailBean(Integer orderdetailid) {
        this.orderdetailid = orderdetailid;
    }

    public OrderdetailBean(Integer orderdetailid, int quantity, double price) {
        this.orderdetailid = orderdetailid;
        this.quantity = quantity;
        this.price = price;
    }

    public Integer getOrderdetailid() {
        return orderdetailid;
    }

    public void setOrderdetailid(Integer orderdetailid) {
        this.orderdetailid = orderdetailid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public SorderBean getOrderid() {
        return orderid;
    }

    public void setOrderid(SorderBean orderid) {
        this.orderid = orderid;
    }

    public ItemBean getItemid() {
        return itemid;
    }

    public void setItemid(ItemBean itemid) {
        this.itemid = itemid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderdetailid != null ? orderdetailid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderdetailBean)) {
            return false;
        }
        OrderdetailBean other = (OrderdetailBean) object;
        if ((this.orderdetailid == null && other.orderdetailid != null) || (this.orderdetailid != null && !this.orderdetailid.equals(other.orderdetailid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Orderdetail[ orderdetailid=" + orderdetailid + " ]";
    }
    
}
