/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shared;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

public class SorderBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer orderid;
    private Date orderdate;
    private double total;
    private int status;
    private Collection<OrderdetailBean> orderdetailCollection;
    private CuserBean emailid;

    public SorderBean() {
    }

    public SorderBean(Integer orderid) {
        this.orderid = orderid;
    }

    public SorderBean(Integer orderid, Date orderdate, double total, int status) {
        this.orderid = orderid;
        this.orderdate = orderdate;
        this.total = total;
        this.status = status;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    
    public Collection<OrderdetailBean> getOrderdetailCollection() {
        return orderdetailCollection;
    }

    public void setOrderdetailCollection(Collection<OrderdetailBean> orderdetailCollection) {
        this.orderdetailCollection = orderdetailCollection;
    }

    public CuserBean getEmailid() {
        return emailid;
    }

    public void setEmailid(CuserBean emailid) {
        this.emailid = emailid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderid != null ? orderid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SorderBean)) {
            return false;
        }
        SorderBean other = (SorderBean) object;
        if ((this.orderid == null && other.orderid != null) || (this.orderid != null && !this.orderid.equals(other.orderid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Sorder[ orderid=" + orderid + " ]";
    }
    
}
