/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shared;

import java.io.Serializable;
import java.util.Collection;

public class ItemtypeBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer itemtypeid;
    private String itemtypename;
    private Collection<ItemBean> itemCollection;

    public ItemtypeBean() {
    }

    public ItemtypeBean(Integer itemtypeid) {
        this.itemtypeid = itemtypeid;
    }

    public ItemtypeBean(Integer itemtypeid, String itemtypename) {
        this.itemtypeid = itemtypeid;
        this.itemtypename = itemtypename;
    }

    public Integer getItemtypeid() {
        return itemtypeid;
    }

    public void setItemtypeid(Integer itemtypeid) {
        this.itemtypeid = itemtypeid;
    }

    public String getItemtypename() {
        return itemtypename;
    }

    public void setItemtypename(String itemtypename) {
        this.itemtypename = itemtypename;
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
        hash += (itemtypeid != null ? itemtypeid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemtypeBean)) {
            return false;
        }
        ItemtypeBean other = (ItemtypeBean) object;
        if ((this.itemtypeid == null && other.itemtypeid != null) || (this.itemtypeid != null && !this.itemtypeid.equals(other.itemtypeid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Itemtype[ itemtypeid=" + itemtypeid + " ]";
    }
    
}
