/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author john
 */
@Entity
@Table(name = "ITEMTYPE", catalog = "", schema = "ADMIN", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ITEMTYPENAME"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Itemtype.findAll", query = "SELECT i FROM Itemtype i"),
    @NamedQuery(name = "Itemtype.findByItemtypeid", query = "SELECT i FROM Itemtype i WHERE i.itemtypeid = :itemtypeid"),
    @NamedQuery(name = "Itemtype.findByItemtypename", query = "SELECT i FROM Itemtype i WHERE i.itemtypename = :itemtypename")})
public class Itemtype implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ITEMTYPEID", nullable = false)
    private Integer itemtypeid;
    @Basic(optional = false)
    @Column(name = "ITEMTYPENAME", nullable = false, length = 20)
    private String itemtypename;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemtypeid")
    private Collection<Item> itemCollection;

    public Itemtype() {
    }

    public Itemtype(Integer itemtypeid) {
        this.itemtypeid = itemtypeid;
    }

    public Itemtype(Integer itemtypeid, String itemtypename) {
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

    @XmlTransient
    public Collection<Item> getItemCollection() {
        return itemCollection;
    }

    public void setItemCollection(Collection<Item> itemCollection) {
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
        if (!(object instanceof Itemtype)) {
            return false;
        }
        Itemtype other = (Itemtype) object;
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
