/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shared;

import java.io.Serializable;

public class AdminBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String adminid;
    private String password;

    public AdminBean() {
    }

    public AdminBean(String adminid) {
        this.adminid = adminid;
    }

    public AdminBean(String adminid, String password) {
        this.adminid = adminid;
        this.password = password;
    }

    public String getAdminid() {
        return adminid;
    }

    public void setAdminid(String adminid) {
        this.adminid = adminid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (adminid != null ? adminid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdminBean)) {
            return false;
        }
        AdminBean other = (AdminBean) object;
        if ((this.adminid == null && other.adminid != null) || (this.adminid != null && !this.adminid.equals(other.adminid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Admin[ adminid=" + adminid + " ]";
    }
    
}
