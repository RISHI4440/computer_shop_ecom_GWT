/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.shared;

import java.io.Serializable;
import java.util.Collection;

public class CompanyBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer companyid;
    private String companyname;
    private Collection<ItemBean> itemCollection;

    public CompanyBean() {
    }

    public CompanyBean(Integer companyid) {
        this.companyid = companyid;
    }

    public CompanyBean(Integer companyid, String companyname) {
        this.companyid = companyid;
        this.companyname = companyname;
    }

    public Integer getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    
    public Collection<ItemBean> getItemCollection() {
        return itemCollection;
    }

    public void setItemCollection(Collection<ItemBean> itemCollection) {
        this.itemCollection = itemCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (companyid != null ? companyid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompanyBean)) {
            return false;
        }
        CompanyBean other = (CompanyBean) object;
        if ((this.companyid == null && other.companyid != null) || (this.companyid != null && !this.companyid.equals(other.companyid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Company[ companyid=" + companyid + " ]";
    }
    
}
