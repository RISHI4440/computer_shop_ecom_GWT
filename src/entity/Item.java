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
@Table(name = "ITEM", catalog = "", schema = "ADMIN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i"),
    @NamedQuery(name = "Item.findByItemid", query = "SELECT i FROM Item i WHERE i.itemid = :itemid"),
    @NamedQuery(name = "Item.findByItemname", query = "SELECT i FROM Item i WHERE i.itemname = :itemname"),
    @NamedQuery(name = "Item.findByModelno", query = "SELECT i FROM Item i WHERE i.modelno = :modelno"),
    @NamedQuery(name = "Item.findByShortdesc", query = "SELECT i FROM Item i WHERE i.shortdesc = :shortdesc"),
    @NamedQuery(name = "Item.findByLongdesc", query = "SELECT i FROM Item i WHERE i.longdesc = :longdesc"),
    @NamedQuery(name = "Item.findByPrice", query = "SELECT i FROM Item i WHERE i.price = :price")})
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ITEMID", nullable = false)
    private Integer itemid;
    @Basic(optional = false)
    @Column(name = "ITEMNAME", nullable = false, length = 20)
    private String itemname;
    @Column(name = "MODELNO", length = 20)
    private String modelno;
    @Column(name = "SHORTDESC", length = 250)
    private String shortdesc;
    @Column(name = "LONGDESC", length = 255)
    private String longdesc;
    @Basic(optional = false)
    @Column(name = "PRICE", nullable = false)
    private double price;
    @JoinColumn(name = "ITEMTYPEID", referencedColumnName = "ITEMTYPEID", nullable = false)
    @ManyToOne(optional = false)
    private Itemtype itemtypeid;
    @JoinColumn(name = "COMPANYID", referencedColumnName = "COMPANYID", nullable = false)
    @ManyToOne(optional = false)
    private Company companyid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemid")
    private Collection<Orderdetail> orderdetailCollection;

    public Item() {
    }

    public Item(Integer itemid) {
        this.itemid = itemid;
    }

    public Item(Integer itemid, String itemname, double price) {
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

    public Itemtype getItemtypeid() {
        return itemtypeid;
    }

    public void setItemtypeid(Itemtype itemtypeid) {
        this.itemtypeid = itemtypeid;
    }

    public Company getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Company companyid) {
        this.companyid = companyid;
    }

    @XmlTransient
    public Collection<Orderdetail> getOrderdetailCollection() {
        return orderdetailCollection;
    }

    public void setOrderdetailCollection(Collection<Orderdetail> orderdetailCollection) {
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
        if (!(object instanceof Item)) {
            return false;
        }
        Item other = (Item) object;
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
