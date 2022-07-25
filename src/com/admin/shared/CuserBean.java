/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.shared;

import java.io.Serializable;
import java.util.Collection;

public class CuserBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String emailid;
    private String password;
    private String username;
    private String address;
    private String city;
    private String phone;
    private String mobile;
    private Collection<SorderBean> sorderCollection;

    public CuserBean() {
    }

    public CuserBean(String emailid) {
        this.emailid = emailid;
    }

    public CuserBean(String emailid, String password) {
        this.emailid = emailid;
        this.password = password;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    
    public Collection<SorderBean> getSorderCollection() {
        return sorderCollection;
    }

    public void setSorderCollection(Collection<SorderBean> sorderCollection) {
        this.sorderCollection = sorderCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (emailid != null ? emailid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CuserBean)) {
            return false;
        }
        CuserBean other = (CuserBean) object;
        if ((this.emailid == null && other.emailid != null) || (this.emailid != null && !this.emailid.equals(other.emailid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Cuser[ emailid=" + emailid + " ]";
    }
    
}
